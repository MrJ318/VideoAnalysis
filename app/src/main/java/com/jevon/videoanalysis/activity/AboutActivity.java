package com.jevon.videoanalysis.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.jevon.videoanalysis.R;
import com.jevon.videoanalysis.databinding.ActivityAboutBinding;

import cn.bmob.v3.update.BmobUpdateAgent;

public class AboutActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        binding.setVm(this);

        // 状态栏文本改为深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // 设置ToolBar
        setSupportActionBar(binding.toolbarAbout);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //设置显示版本号
        try {
            PackageInfo pack = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String versonname = pack.versionName;
            binding.textVersion.setText(String.format(getResources().getString(R.string.version), versonname));
        } catch (PackageManager.NameNotFoundException e) {
            binding.textVersion.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }
    }

    // 点击事件
    public void viewOnClick(View v) {
        switch (v.getId()) {
            case R.id.text_CheckVersion:
                checkPermission();
                break;
            case R.id.text_help:
                String msg = "1.在软件主页打开相应的视频网站\n2.打开视频播放界面，点击右上角的菜单，" +
                        "选择线路一即可进入免VIP播放界面\n3.若播放失败，先返回上一页，在尝试选择其他线路";
                showDialog("使用帮助", msg);
                break;
            case R.id.text_github:
                Intent view = new Intent();
                view.setAction(Intent.ACTION_VIEW);
                view.setData(Uri.parse("https://github.com/MrJ318/VideoAnalysis"));
                startActivity(view);
                break;
            case R.id.text_disclaimer:
                showDialog("免责声明", "1.所有视频资源均来自网络，本软件只做整理，版权归原作者所有\n2.仅供个人交流学习使用，不做任何商业用途");
                break;

        }
    }

    // 显示dialog
    private void showDialog(String title, String message) {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", null);
        builder.show();
    }

    // 检查权限
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                BmobUpdateAgent.forceUpdate(this);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            BmobUpdateAgent.forceUpdate(this);
        } else {
            Toast.makeText(this, "读写文档权限被拒绝，无法执行检查更新！", Toast.LENGTH_SHORT).show();
        }
    }
}
