package com.example.seebuses.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.seebuses.R
import com.example.seebuses.core.SaveModule
import com.example.seebuses.core.clear
import com.example.seebuses.core.data.ControlVars
import com.example.seebuses.core.data.CoreData.mc
import com.example.seebuses.core.data.CoreData.transports
import com.example.seebuses.core.entities.Metro

class Schema_Metro_Add : AppCompatActivity() {

    private var goBackBtn: Button? = null
    private var choose: Button? = null
    private var chooseSchema: Button? = null
    private var schemaCity: String? = null
    private var schemaFakeCity: String? = null
    private var schema: Metro? = null
    private var SchemaTitle: TextView? = null
    private var SchemaBlockText: TextView? = null
    private var acceptFlag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schema_metro_add)
        startSetUp()
        setTextSize()
        registerForContextMenu(choose)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        goMain(this.currentFocus)
    }

    fun goMain(view: View?) {
        val goMain = Intent(this, MainActivity::class.java)
        startActivity(goMain)
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        createContextMenu(menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    private fun createContextMenu(menu: ContextMenu) {
        menu.setHeaderTitle(R.string.CityWithMetro)
        for (mcin in mc) {
            menu.add(mcin[0])
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val cityName = item.title as String?
        for (strArr in mc) {
            if (strArr[0] == cityName) {
                schemaFakeCity = strArr[1]
                acceptFlag++
            }
        }
        enterData(cityName)
        return super.onContextItemSelected(item)
    }

    override fun onStop() {
        mc.clear()
        super.onStop()
    }

    fun onAccept(view: View?) {
        schema = Metro(schemaCity, schemaFakeCity, if (ControlVars.IS_RUSSIAN) "Метро" else "Metro")
        if (acceptSchema() && acceptFlag != 0) {
            val viewPointer =
                MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer)
            transports[viewPointer] = schema
            SaveModule.saveTransportBlocksData()
            val returnMain = Intent(this, MainActivity::class.java)
            startActivity(returnMain)
        }
    }

    private fun acceptSchema(): Boolean {
        if (transports.contains(schema)) {
            Toast.makeText(this, R.string.SuchSchema, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun enterData(cityToAccept: String?) {
        schemaCity = cityToAccept
        choose!!.text = cityToAccept
        choose!!.setBackgroundColor(Color.GREEN)
    }

    private fun startSetUp() {
        choose = findViewById(R.id.SchemaBlockChoose)
        goBackBtn = findViewById(R.id.goBackBtn)
        SchemaTitle = findViewById(R.id.SchemaTitle)
        SchemaBlockText = findViewById(R.id.SchemaBlockText)
        chooseSchema = findViewById(R.id.chooseSchema)
    }

    private fun setTextSize() {
        SchemaTitle!!.textSize = (ControlVars.CURRENT_TEXT_SIZE + 6).toFloat()
        SchemaBlockText!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        choose!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        chooseSchema!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        goBackBtn!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
    }
}