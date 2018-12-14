package me.mehdi.lollipoptransitions

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.transition.TransitionInflater
import android.view.View
import android.widget.Button

class DetailActivity : AppCompatActivity() {

    val mContext: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mStart: Button = findViewById(R.id.start)
        mStart.setOnClickListener {
            var intent: Intent = Intent(this, AboutActivity::class.java)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())

            //Set up exit and reenter transitions
            window.exitTransition = TransitionInflater.from(mContext).inflateTransition(R.transition.window_exit)
            window.reenterTransition = TransitionInflater.from(mContext).inflateTransition(R.transition.window_reenter)

        }


    }
}
