package com.tencent.qcloud.suixinbo.views.customviews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.tencent.av.PingResult;
import com.tencent.av.ServerInfo;
import com.tencent.av.TIMAvManager;
import com.tencent.av.TIMPingCallBack;
import com.tencent.qcloud.suixinbo.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 测速对话框
 */
public class SpeedTestDialog {

    private final String TAG = "SpeedTestDialog";

    private ProgressDialog pd;
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private Context context;
    private List<PingResult> results = new ArrayList<>();
    private final int MSG_START = 1;
    private final int MSG_PROGRESS =2;
    private final int MSG_END = 3;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_START:
                    pd = ProgressDialog.show(context, context.getString(R.string.ping_ing), context.getString(R.string.ping_start));
                    break;
                case MSG_PROGRESS:
                    String pdMsg = msg.getData().getString("msg");
                    pd.setMessage(pdMsg);
                    break;
                case MSG_END:
                    pd.dismiss();
                    StringBuilder resultStr = new StringBuilder();
                    for (PingResult result : results){
                        resultStr.append(result.getServer().ip);
                        resultStr.append(context.getString(R.string.ping_time) + result.getUseTime() + "ms ");
                        resultStr.append(context.getString(R.string.ping_miss) + percentFormat.format((double)(result.getTotalPkg() - result.getReceivePkg())/(double)result.getTotalPkg()) + "\n");
                    }
                    new AlertDialog.Builder(context).setMessage(resultStr.toString()).show();
                    break;
            }
        }
    };

    public SpeedTestDialog(Context context){
        this.context = context;
        percentFormat.setMinimumFractionDigits(1);

    }

    public void start(){
        TIMAvManager.getInstance().requestSpeedTest((short) 7, (short) 6, 0, new TIMPingCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e(TAG, "ping failed. code: " + code + " desc: " + desc);
            }

            @Override
            public void onSuccess(PingResult t) {
                Log.d(TAG, "end test " + t.getServer().ip + " avg timeuse:" + t.getUseTime());
                results.add(t);

            }

            @Override
            public void onProgress(ServerInfo serverInfo, int totalPkg, int currentPkg) {
                Message message = new Message();
                message.what = MSG_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putString("msg", serverInfo.ip.toString() + context.getString(R.string.ping_progress) + " " + currentPkg + "/" + totalPkg);
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onStart(List<ServerInfo> serverInfoList) {
                Log.d(TAG, "start test " + serverInfoList.size() + " ip");
                if (serverInfoList.size() > 0){
                    Message message = new Message();
                    message.what = MSG_START;
                    handler.sendMessage(message);
                }else{
                    Toast.makeText(context, context.getString(R.string.ping_no_server), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFinish() {
                Message message = new Message();
                message.what = MSG_END;
                handler.sendMessage(message);
            }
        });
    }

    public List<PingResult> getResults() {
        return results;
    }
}
