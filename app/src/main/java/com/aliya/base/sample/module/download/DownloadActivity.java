package com.aliya.base.sample.module.download;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aliya.base.sample.R;
import com.aliya.base.sample.base.ActionBarActivity;
import com.aliya.base.sample.databinding.ActivityDownloadBinding;

import java.io.File;
import java.net.URLConnection;

/**
 * 使用系统下载管理器下载文件
 *
 * @author a_liYa
 * @date 2020/12/18 10:27.
 */
public class DownloadActivity extends ActionBarActivity implements View.OnClickListener {

    ActivityDownloadBinding mBinding;

    String pdfUrl = "https://download.brother.com/welcome/docp000648/cv_pt3600_schn_sig_lad962001" +
            ".pdf";
    String apkUrl = "https://fga1.market.xiaomi" +
            ".com/download/AppStore/0f974b54777404f7339c3cc3f8179d7a6805656d1/com.mazda_china" +
            ".operation.imazda.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDownloadBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.tvDownloadPdf.setOnClickListener(this);
        mBinding.tvDownloadApk.setOnClickListener(this);

        Uri uri = Uri.parse(apkUrl);
        String path = uri.getPath();
        String filename = path.substring(path.lastIndexOf(File.separator) + 1);
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        String contentType = URLConnection.guessContentTypeFromName(extensionName);
        Log.e("TAG", extensionName + ": MimeType = " + contentType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_download_pdf:
                startService(DownloadService.newIntent(pdfUrl));
                break;
            case R.id.tv_download_apk:
                startService(DownloadService.newIntent(apkUrl));
                break;
        }
    }
}