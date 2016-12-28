package somdosbichos.cursoandroid.com.somdosbichos;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imagemCao;
    private ImageView imagemGato;
    private ImageView imagemLeao;
    private ImageView imagemMacaco;
    private ImageView imagemOvelha;
    private ImageView imagemVaca;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemCao = (ImageView) findViewById(R.id.imgCaoId);
        imagemGato = (ImageView) findViewById(R.id.imgGatoId);
        imagemLeao = (ImageView) findViewById(R.id.imgLeaoId);
        imagemMacaco = (ImageView) findViewById(R.id.imgMacacoId);
        imagemOvelha = (ImageView) findViewById(R.id.imgOvelhaId);
        imagemVaca = (ImageView) findViewById(R.id.imgVacaId);

        imagemCao.setOnClickListener(this);
        imagemGato.setOnClickListener(this);
        imagemLeao.setOnClickListener(this);
        imagemMacaco.setOnClickListener(this);
        imagemOvelha.setOnClickListener(this);
        imagemVaca.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imgCaoId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cao);
                tocarSom();
                break;
            case R.id.imgGatoId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gato);
                tocarSom();
                break;
            case R.id.imgLeaoId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.leao);
                tocarSom();
                break;
            case R.id.imgMacacoId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.macaco);
                tocarSom();
                break;
            case R.id.imgOvelhaId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ovelha);
                tocarSom();
                break;
            case R.id.imgVacaId:
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.vaca);
                tocarSom();
                break;
        }
    }

    private void tocarSom() {
        if (mediaPlayer != null){
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    mp = null;
                }
            });
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
