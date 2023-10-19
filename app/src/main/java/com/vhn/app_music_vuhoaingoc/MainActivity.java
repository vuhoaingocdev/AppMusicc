package com.vhn.app_music_vuhoaingoc;


import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtTitle, txtTimeTotal, txtTimeSong;
    SeekBar skSong;
    ImageButton btnPrev, btnPlay, btnStop, btnNext;

    ImageView imgHinh;

    ArrayList <Song> arraySong;

    MediaPlayer mediaPlayer;

    Animation animation;

    Animation animationTranslate;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();
        KhoiTaoMediaPlayer();
        animation = AnimationUtils.loadAnimation(this, R.anim.dic_rotate);

        animationTranslate = AnimationUtils.loadAnimation(this, R.anim.title_translate);
        //bắt sự kiện play
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);

                }
                else
                {

                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.stop);
                }
                setTimeSong();
                UpdateTimeSong();
                imgHinh.startAnimation(animation);
                txtTitle.startAnimation(animationTranslate);
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position  > arraySong.size() - 1)
                {
                    position = 0;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();

                    KhoiTaoMediaPlayer();
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.stop);

                    setTimeSong();
                    UpdateTimeSong();
                }
            }
        });


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position  < 0)
                {
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.stop);
                setTimeSong();
                UpdateTimeSong();
            }
        });


        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void AnhXa()
    {
        txtTimeTotal = (TextView) findViewById(R.id.textviewTotal);
        txtTimeSong  = (TextView) findViewById(R.id.textviewSong);
        txtTitle     = (TextView) findViewById(R.id.textviewTitle);
        skSong       = (SeekBar) findViewById(R.id.seekbarTime);
        btnPrev      = (ImageButton) findViewById(R.id.imagebuttonPrev);
        btnPlay      = (ImageButton) findViewById(R.id.imagebuttonPlay);
        btnStop      = (ImageButton) findViewById(R.id.imagebuttonStop);
        btnNext      = (ImageButton) findViewById(R.id.imagebuttonNext);
        imgHinh      = (ImageView) findViewById(R.id.imageViewHinh);
    }

    private void AddSong()
    {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Anh nhớ em nhiều lắm", R.raw.anh_nho_em_nhieu_lam));
        arraySong.add(new Song("Mình cùng nhau đóng băng", R.raw.minh_cungnhaudongbang));
        arraySong.add(new Song("Cho tôi xin một vé đi tuổi thơ", R.raw.mot_ve_tuoi_tho));
        arraySong.add(new Song("Nếu là anh", R.raw.neu_la_anh));
        arraySong.add(new Song("Ngày ấy bạn và tôi", R.raw.ngay_ay_ban_va_toi));
        arraySong.add(new Song("Nơi ấy con tìm về", R.raw.noi_ay_con_tim_ve));
        arraySong.add(new Song("Phía sau một cô gái", R.raw.phia_sau_mot_co_gai));
    }

    //Hàm khởi tạo MediaPlayer
    private void KhoiTaoMediaPlayer()
    {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }


    private void setTimeSong()
    {

        //Hàm getDuration() -> lấy toàn bộ thời gian của bài hát

         SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
         txtTimeSong.setText((dinhDangGio.format(mediaPlayer.getDuration())));

//        skSong.setMax(mediaPlayer.getDuration()); -> lấy độ dài mã của thanh seekbar  gán cho thời gina của bài hát

        skSong.setMax(mediaPlayer.getDuration());
    }

    //Hàm cập nhật thời gian theo bài hát
    private void UpdateTimeSong()
    {
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                txtTimeTotal.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));

                //Hàm getCurrentPosition() -> cập nhật thời gian bài hát bài hát chạy đến đâu thì thanh seekbar chạy đến đó

                skSong.setProgress(mediaPlayer.getCurrentPosition());


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position  > arraySong.size() - 1)
                        {
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying())
                            mediaPlayer.stop();

                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.stop);

                        setTimeSong();
                        UpdateTimeSong();
                    }
                });

                handler.postDelayed(this, 500);
            }
        },100);
    }

}