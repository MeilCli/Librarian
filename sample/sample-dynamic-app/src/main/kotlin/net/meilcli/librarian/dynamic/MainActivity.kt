package net.meilcli.librarian.dynamic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.meilcli.librarian.activities.NoticesActivity
import net.meilcli.librarian.serializers.NoticesReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            startActivity(NoticesActivity.createIntent(this, NoticesReader(), "notices.json"))
        }
    }
}