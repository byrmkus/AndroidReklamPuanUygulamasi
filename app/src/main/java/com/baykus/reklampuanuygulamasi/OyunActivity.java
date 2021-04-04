package com.baykus.reklampuanuygulamasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class OyunActivity extends AppCompatActivity {
    private AdView banner;
    private InterstitialAd interstitialAd;
    private RewardedAd mRewardedAd;
    private RewardedAdLoadCallback rewardedAdLoadCallback;
    private TextView textViewOyunEkrani, textViewPuan;
    private Button buttonPuanKazan, buttonSonrakiBolum;
    private int puan = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);
        banner = findViewById(R.id.adView);
        textViewOyunEkrani = findViewById(R.id.textViewOyunEkrani);
        textViewPuan = findViewById(R.id.textViewPuan);
        buttonPuanKazan = findViewById(R.id.buttonPuan);
        buttonSonrakiBolum = findViewById(R.id.buttonSonrakiBolum);

        buttonPuanKazan.setVisibility(View.INVISIBLE);

        MobileAds.initialize(OyunActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        bannerKurulumu();
        interstitialKurulum();
        rewardedAdsKurulum();

        buttonPuanKazan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mRewardedAd.isLoaded()){
                    mRewardedAd.show(OyunActivity.this, new RewardedAdCallback() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {



                        }

                        @Override
                        public void onRewardedAdClosed() {
                            puan = puan +20;
                            buttonPuanKazan.setVisibility(View.INVISIBLE);
                            Toast.makeText(OyunActivity.this,"20 Puan Kazandınız",Toast.LENGTH_SHORT).show();
                            textViewPuan.setText("Toplam Puan :" + puan);
                        }
                    });
                }


            }
        });

        buttonSonrakiBolum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (puan>=30){
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    }
                }else{
                    Toast.makeText(OyunActivity.this,
                            "Sonraki Bölüme Geçmek İçin 30 puan almanız gerekmektedir",
                            Toast.LENGTH_SHORT).show();
                    buttonPuanKazan.setVisibility(View.VISIBLE);
                }



            }


        });

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startActivity(new Intent(OyunActivity.this, SonrakiBolumActivity.class));
                finish();
            }
        });

    }

    public void bannerKurulumu() {

        banner.loadAd(new AdRequest.Builder().build());
    }

    public void interstitialKurulum() {

        interstitialAd = new InterstitialAd(OyunActivity.this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    public void rewardedAdsKurulum(){
    mRewardedAd = new RewardedAd(OyunActivity.this,
            "ca-app-pub-3940256099942544/5224354917");

    rewardedAdLoadCallback = new RewardedAdLoadCallback(){
        @Override
        public void onRewardedAdLoaded() {
            Log.e("Yukleme Listener","onRewardedAdLoaded Çalıştı");
        }
    };

    mRewardedAd.loadAd(new AdRequest.Builder().build(),rewardedAdLoadCallback);

    }
}