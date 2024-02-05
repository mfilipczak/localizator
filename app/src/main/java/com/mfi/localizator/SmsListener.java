package com.mfi.localizator;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class SmsListener extends BroadcastReceiver {
    String message = null;          // Holds the message received from the person using the app.
    String filename = "smslog.txt"; // Filename of the text file which hold the messages.

    FileOutputStream outputStream = null;       // Used to write to the text file.
    FileInputStream in;                         // Used to read from the text file.
    InputStreamReader inputStreamReader = null;
    BufferedReader bufferedReader = null;

    StringBuilder sb = null;  // Holds the final string containing all the contents of the text file.
    String line = null;       // Holds each line of the text file (used only when reading from the file).

    SmsManager sms = SmsManager.getDefault(); // Manages SMS operations such as sending data, text, and pdu SMS messages.

    File file = null;           // Holds the contents of the text message in the file, along with the sender's phone number.
    String messageBody = null;  // Holds the body of the SMS message.
    String sender = null;       // Holds the sender's phone number.

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast. In this case,
     * the action we are listening for is when an SMS is received.
     * @param context  The Context in which the receiver is running.
     * @param intent   The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            Intent intent2 = new Intent(context, MainActivity.class);
            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
           // Toast.makeText(context, "Wiadomosc:" + messageBody, Toast.LENGTH_LONG).show();
            // If the intent received is a SMS received action.
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction()))
            {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent))
                {
                    messageBody = smsMessage.getDisplayMessageBody();   // The contents of the message.
                    sender = smsMessage.getDisplayOriginatingAddress(); // The phone number of the sender.

                    /*
                     * Create a Toast notification that shows the contents of the message,
                     * along with the sender's phone number.
                     *
                     * Note: Simply comment the following three lines to hide the Toast notification.
                     */
                  //  int d = Toast.LENGTH_LONG;
                //    Toast t = Toast.makeText(context, "Received from: " + sender + " Message: " + messageBody, d);
                  //  t.show();
                    if(messageBody.equals("gdzie")) {
                        intent2.putExtra("sendTo", sender);
                        intent2.putExtra("alarm", true);
                        context.startActivity(intent2);
                        intent2.putExtra("sendTo", sender);
                        intent2.putExtra("alarm", true);
                        context.startActivity(intent2);
                    }
                }
            }
        }
        catch (Exception e)
        {
          //  Toast.makeText(context, "Blad:" + e.getMessage(), Toast.LENGTH_LONG).show();
          //  Log.e("SMSListener", "SMS Listener Exception " + e);
        }
    }
}