package zhaoxizhang.github.io.gsonenhance

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import zhaoxizhang.github.io.gsonenhance.test.GsonTestManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            GsonTestManager.getInstance().action()
        }
    }
}