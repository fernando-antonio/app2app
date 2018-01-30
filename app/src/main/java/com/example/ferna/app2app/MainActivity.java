package com.example.ferna.app2app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String PAG_SEGURO_PACKAGE_NAME = "br.com.uol.ps";

    private static final String PAG_SEGURO_CLASS_NAME = "br.com.uol.ps.app.MainActivity";

    private static final String FLAG_APP_PAYMENT_VALUE = "FLAG_APP_PAYMENT_VALUE";
    private static final String FLAG_INSTALLMENT_NUMBER = "FLAG_INSTALLMENT_NUMBER";

    private static final int REQUEST_CODE = 123; // Pode ser o número de sua escolha



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( checkIfAppIsInstalled()){
            Toast.makeText(getApplicationContext(), "Aplicativo instalado ", Toast.LENGTH_SHORT).show();
            sellWhitCreditCard();

        }
    }

    private boolean checkIfAppIsInstalled() {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(PAG_SEGURO_PACKAGE_NAME, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    private void sellWhitCreditCard(){
        BigDecimal paymentValue = new BigDecimal("2.00");
        Toast.makeText(getApplicationContext(), "Realizando uma tentativa de venda ", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(Intent.ACTION_MAIN);
        it.setClassName(PAG_SEGURO_PACKAGE_NAME, PAG_SEGURO_CLASS_NAME); // Intent que será chamada.
        it.putExtra(FLAG_APP_PAYMENT_VALUE, paymentValue); // Valor da venda.
        it.putExtra(FLAG_INSTALLMENT_NUMBER, 1); // Valor da venda.

        try {
            startActivityForResult(it, REQUEST_CODE); // Chama o aplicativo do PagSeguro.
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Aplicativo PagSeguro não instalado", Toast.LENGTH_SHORT).show();
        }
    }
    private static final String FLAG_RESULT_ERROR_CODE = "FLAG_RESULT_ERROR_CODE";

    private static final String FLAG_RESULT_ERROR_MESSAGE = "FLAG_RESULT_ERROR_MESSAGE";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_CANCELED) {
        //TODO: Apresentar mensagem de erro ao usuário informando que a operação foi cancelada
        } else if (resultCode == RESULT_OK) {

            if (data.hasExtra(FLAG_RESULT_ERROR_MESSAGE)) {
                // Extração das informações sobre o erro
                String errorCode = data.getStringExtra(FLAG_RESULT_ERROR_CODE);
                String errorMessage = data.getStringExtra(FLAG_RESULT_ERROR_MESSAGE);
                Toast.makeText(getApplicationContext(), "Código do erro: "+ errorCode, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Mensagem do erro: "+ errorMessage, Toast.LENGTH_SHORT).show();
                // TODO: Lógica para apresentação das informações de erro
            } else {
                // Extração das informações sobre a transação
                String transactionCode = data.getStringExtra("FLAG_TRANSACTION_CODE");
                String paymentMethod = data.getStringExtra("FLAG_PAYMENT_METHOD");
                String cardBrand = data.getStringExtra("FLAG_CARD_BRAND");
                String cardHolder = data.getStringExtra("FLAG_CARD_HOLDER");
               // BigDecimal paymentAmount = (BigDecimal) data.getSerializableExtra("FLAG_PAYMENT_AMOUNT", 0);
                int installmentNumber = data.getIntExtra("FLAG_INSTALLMENT_NUMBER", 0);
               // BigDecimal installmentAmount = (BigDecimal) data.getSerializableExtra("FLAG_PAYMENT_INSTALLMENT", 0);
                Calendar time = (Calendar) data.getSerializableExtra("FLAG_PAYMENT_TIME");
                // TODO: Lógica para apresentação dos dados da transação
            }
        }
    }
}
