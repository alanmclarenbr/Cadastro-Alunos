package br.com.caelum.cadastro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDao;

/**
 * Class Created by android6196 on 05/11/16.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Chegou um SMS!!!", Toast.LENGTH_LONG).show();
        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[])bundle.get("pdus");

        byte[] mensagem = (byte[])mensagens[0];

        String formato = (String) bundle.get("format");
        SmsMessage sms = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sms = SmsMessage.createFromPdu(mensagem, formato);
            AlunoDao dao = new AlunoDao(context);
            if(dao.isAluno(sms.getDisplayOriginatingAddress())) {
                Toast.makeText(context, sms.getDisplayOriginatingAddress(), Toast.LENGTH_LONG).show();
                MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
                mp.start();
            }
        }

    }
}
