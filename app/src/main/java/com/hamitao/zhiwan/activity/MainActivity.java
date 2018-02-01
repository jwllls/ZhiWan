package com.hamitao.zhiwan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hamitao.zhiwan.R;
import com.hamitao.zhiwan.fragment.MeFragment;
import com.hamitao.zhiwan.fragment.RecommendFragment;
import com.hamitao.zhiwan.fragment.SquareFragment;
import com.hamitao.zhiwan.fragment.WechatFragment;
import com.zhiwan.hamitao.base_module.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

//public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
public class MainActivity extends BaseActivity {

    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;

    @BindViews({R.id.iv_recommend, R.id.iv_wechat, R.id.iv_square, R.id.iv_me})
    List<ImageView> tab_ivs;
    @BindViews({R.id.tab_recommend, R.id.tab_wechat, R.id.tab_square, R.id.tab_me})
    List<View> tabs;
    @BindViews({R.id.tv_recommend, R.id.tv_wechat, R.id.tv_square, R.id.tv_me})
    List<TextView> tab_tvs;

    @BindView(R.id.tv_unread_num)
    TextView tv_unread_num;
    @BindView(R.id.tv_music)
    TextView tv_music;


    private FragmentManager fragmentManager;
    private RecommendFragment recommendFragment;
    private WechatFragment wechatFragment;
    private SquareFragment squareFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState != null) {
            //是否为异常崩溃之后启动方式或者是后台长时间被系统回收重启
            if (savedInstanceState.getBoolean("isExceptionStart", false)) {
                startActivity(new Intent(this, StartActivity.class));
                finish();
                return;
            }
        }
        initData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("isExceptionStart", true);
    }


    //初始化
    private void initData() {
//        requestCodeQRCodePermissions();
        fragmentManager = getSupportFragmentManager();
        //默认选中第一个tab
        showFragment(1);
    }


    //    ---------------------------------------------Fragment显藏-----------------------------------------------
    private void showFragment(int page) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (page) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (recommendFragment != null)
                    ft.show(recommendFragment);
                    // 否则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    recommendFragment = new RecommendFragment();
                    ft.add(R.id.fragment_container, recommendFragment);
                }
                //选中第一个Tab时的状态
                tab_ivs.get(0).setImageResource(tabResId_p[0]);
                tab_tvs.get(0).setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            case 2:
                if (wechatFragment != null)
                    ft.show(wechatFragment);
                else {
                    wechatFragment = new WechatFragment();
                    ft.add(R.id.fragment_container, wechatFragment);
                }
                break;
            case 3:
                if (squareFragment != null) {
                    ft.show(squareFragment);
                } else {
                    squareFragment = new SquareFragment();
                    ft.add(R.id.fragment_container, squareFragment);
                }
                break;
            case 4:
                if (meFragment != null) {
                    ft.show(meFragment);
                } else {
                    meFragment = new MeFragment();
                    ft.add(R.id.fragment_container, meFragment);
                }
                break;
        }
        ft.commit();
    }

    // 当fragment已被实例化，相当于发生过切换，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (recommendFragment != null)
            ft.hide(recommendFragment);
        if (wechatFragment != null)
            ft.hide(wechatFragment);
        if (squareFragment != null)
            ft.hide(squareFragment);
        if (meFragment != null)
            ft.hide(meFragment);
    }


    //    ---------------------------------------------点击事件----------------------------------------------------
    private int index;
    private int currentTabIndex;// 当前fragment的index
    private int tabResId[] = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private int tabResId_p[] = {R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};

    public void onTabClick(View v) {
        //每次点击遍历下标
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i) == v) {
                index = i;
                break;
            }
        }
        if (currentTabIndex != index) {
            showFragment(index + 1);
            tab_ivs.get(currentTabIndex).setImageResource(tabResId[currentTabIndex]);
            tab_tvs.get(currentTabIndex).setTextColor(ContextCompat.getColor(this, R.color.black));

        }
        tab_ivs.get(index).setImageResource(tabResId_p[index]);
        tab_tvs.get(index).setTextColor(ContextCompat.getColor(this, R.color.white));
        currentTabIndex = index;
    }

    @OnClick({R.id.tab_recommend, R.id.tab_wechat, R.id.tab_square, R.id.tab_me, R.id.tv_music})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_recommend:
                onTabClick(tabs.get(0));
                break;
            case R.id.tab_wechat:
                onTabClick(tabs.get(1));
                break;
            case R.id.tab_square:
                onTabClick(tabs.get(2));
                break;
            case R.id.tab_me:
                onTabClick(tabs.get(3));
                break;
            case R.id.tv_music:
                startActivity(new Intent(this, MusicActivity.class));
                break;
        }
    }


    //    ---------------------------------------------权限请求----------------------------------------------------
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
//    private void requestCodeQRCodePermissions() {
//        String[] perms = {
//                Manifest.permission.CAMERA,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.RECORD_AUDIO
//        };
//        if (!EasyPermissions.hasPermissions(this, perms)) {
//            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, perms);
//        }
//    }
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Log.e("permission", "请求成功");
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Log.e("permission", "请求失败");
//    }
    //    ---------------------------------------------权限请求----------------------------------------------------


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 101) {
            wechatFragment.setGroupName(data.getStringExtra("groupName"));
        }
    }
}
