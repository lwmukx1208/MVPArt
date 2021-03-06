package me.jessyan.mvpart.demo;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.art.base.BaseActivity;
import me.jessyan.art.mvp.IView;
import me.jessyan.art.mvp.Message;
import me.jessyan.mvpart.demo.demo2.SecondActivity;
import me.jessyan.mvpart.demo.demo3.ThirdDialog;
import me.jessyan.mvpart.demo.demo4.FourthActivity;

/**
 *
 * 此框架为轻量级框架,针对中小型项目,如果页面逻辑过于复杂的话,那就可以使用传统MVP
 * 只是封装了view和presenter,model层需要自行封装,后面会开个分支提交比较完整的框架,像MVPArms一样
 * demo中没有网络请求,presenter中只是模拟了请求,如果你会使用Handler的话,相信你会很快理解此框架
 * 其实传统MVP每个页面对应一个presenter,而大多数presenter只有一两个方法,这样导致存在大量代码寥寥无几的**Presenter**
 * 区别于传统MVP模式,Activity持有Presenter,但是Presenter并不直接持有view
 * 每个Presenter方法通过message间接持有view,每个方法执行完就自动释放view的引用
 * 这样做是想让大家重用presenter时,不必关注presenter中的全部方法,是否都适合重用
 * 哪怕其中只有一个方法可以重用,那也可以直接重用整个presenter,这样可以减少大量类的创建
 * 并且presenter都通过一个handleMessage方法与view通信,重用presenter,Activity并不会实现其他不需要的方法
 * 方法执行完即表示和view的关系解除
 * 当然很多不同的逻辑都写在一个Presenter中,虽然可以少写很多类,但是后面的扩展性肯定不好,所以这个粒度需要自己控制,但是对于外包项目简直是福音
 */
public class MainActivity extends BaseActivity<MainPresenter> implements IView {

    @BindView(R.id.tv_main)
    TextView mTextView;
    @BindView(R.id.activity_main)
    RelativeLayout mRoot;
    private ThirdDialog mDialog;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mDialog = new ThirdDialog();
    }

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case 0:
                mRoot.setBackgroundResource(message.arg1);
                break;
            case 1:
                mTextView.setText(message.str);
                break;
        }
    }


     @OnClick({R.id.btn_request,R.id.btn_second,R.id.btn_third,R.id.btn_fourth})
         public void onClick(View v) {
             switch (v.getId()) {
                 case R.id.btn_request:
                     mPresenter.request(Message.obtain(this,"jess!"));
                     break;
                 case R.id.btn_second:
                     startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                     break;
                 case R.id.btn_third:
                     mDialog.show(getSupportFragmentManager(),"dialog");
                     break;
                 case R.id.btn_fourth:
                     startActivity(new Intent(getApplicationContext(),FourthActivity.class));
                     break;
             }
         }

}
