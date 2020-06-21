package net.meilcli.librarian.sample.custom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.meilcli.librarian.serializers.NoticesReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            startActivity(CustomNoticesActivity.createIntent(this, NoticesReader(), "notices.json"))
        }
    }
}
