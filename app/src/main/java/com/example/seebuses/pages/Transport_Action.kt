package com.example.seebuses.pages

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.seebuses.core.data.CoreData.ct
import com.example.seebuses.core.data.CoreData.transports
import com.example.seebuses.core.entities.BlockElement
import com.example.seebuses.core.entities.WheelTransport
import com.google.android.material.textfield.TextInputEditText

class Transport_Action : AppCompatActivity() {

    private var chooseTransportNum: TextInputEditText? = null
    private var transportBlock: BlockElement? = null
    private var transpNumb: String? = null
    private var transpCity: String? = null
    private var typeForSearch: String? = null
    private var fakeCity: String? = null
    private var viewText: String? = null
    private var insertTranspTypeText: TextView? = null
    private var chooseTranspType: TextView? = null
    private var chooseTranspCity: TextView? = null
    private var choseBtn: Button? = null
    private var ApplyBtn: Button? = null
    private var CancelBtn: Button? = null
    private var choseCityBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_transport)
        startSetUp()
        setTextSize()
        registerForContextMenu(choseBtn)
        registerForContextMenu(choseCityBtn)
        chooseTransportNum!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //при введении символа
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                //во время введения символа
            }

            override fun afterTextChanged(editable: Editable) {
                transpNumb = editable.toString()
            }
        })
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        addMenu(menu, v)
    }

    private fun addMenu(menu: ContextMenu, v: View) {
        if (v.id != R.id.choseCityBtn) {
            menu.setHeaderTitle(R.string.TransportTypes)
            menu.add(R.string.Bus)
            menu.add(R.string.Trolleybus)
            menu.add(R.string.Tram)
        } else {
            menu.setHeaderTitle(R.string.Cities)
            for (block in ct) {
                menu.add(block[0])
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (ControlVars.IS_RUSSIAN) {
            itemSelected_ruLocale(item)
        } else itemSelected_enLocale(item)
        return super.onContextItemSelected(item)
    }

    private fun itemSelected_ruLocale(item: MenuItem) {
        when (item.title as String) {
            "Автобус" -> {
                typeForSearch = "citybus"
                viewText = "Автобус"
                choseBtn!!.setText(R.string.Bus)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            "Троллейбус" -> {
                typeForSearch = "trolleybus"
                viewText = "Троллейбус"
                choseBtn!!.setText(R.string.Trolleybus)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            "Трамвай" -> {
                typeForSearch = "tram"
                viewText = "Трамвай"
                choseBtn!!.setText(R.string.Tram)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            else -> {
                transpCity = item.title as String?
                choseCityBtn!!.text = transpCity
                choseCityBtn!!.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun itemSelected_enLocale(item: MenuItem) {
        when (item.title as String) {
            "Bus" -> {
                typeForSearch = "citybus"
                viewText = "Bus"
                choseBtn!!.setText(R.string.Bus)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            "Trolleybus" -> {
                typeForSearch = "trolleybus"
                viewText = "Trolleybus"
                choseBtn!!.setText(R.string.Trolleybus)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            "Tram" -> {
                typeForSearch = "tram"
                viewText = "Tram"
                choseBtn!!.setText(R.string.Tram)
                choseBtn!!.setBackgroundColor(Color.GREEN)
            }

            else -> {
                transpCity = item.title as String?
                choseCityBtn!!.text = transpCity
                choseCityBtn!!.setBackgroundColor(Color.GREEN)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        goMain(this.currentFocus)
    }

    fun goMain(view: View?) {
        val goMain = Intent(this, MainActivity::class.java)
        startActivity(goMain)
    }

    override fun onStop() {
        ct.clear()
        super.onStop()
    }

    fun onAccept(view: View?) {
        checkCity()
        if (acceptNonNull()) {
            if (acceptTransport()) {
                val viewPointer =
                    MainActivity.transportBlocks.indexOfChild(MainActivity.BlockView_pointer)
                transports[viewPointer] = transportBlock
                SaveModule.saveTransportBlocksData()
                goMain(this.currentFocus)
            }
        }
    }

    private fun acceptNonNull(): Boolean {
        //TODo заменить нижнее подчёркивание
        if (transpNumb != null && typeForSearch != null && transpCity != null) {
            val transpNumber = transpNumb!!.toInt()
            transportBlock = WheelTransport(
                transpCity,
                fakeCity,
                typeForSearch,
                viewText + " " + transpNumber,
                transpNumber
            )
            return true
        } else {
            Toast.makeText(this@Transport_Action, R.string.FillAllData, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun acceptTransport(): Boolean {
        if (transports.contains(transportBlock)) {
            Toast.makeText(this, R.string.SuchTransp, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkCity() {
        for (i in ct.indices) {
            val scanString = ct[i]
            if (scanString[0] == transpCity) {
                fakeCity = scanString[1]
            }
        }
    }

    private fun startSetUp() {
        chooseTransportNum = findViewById(R.id.chooseTransportNum)
        insertTranspTypeText = findViewById(R.id.insertTranspTypeText)
        chooseTranspType = findViewById(R.id.chooseTranspType)
        chooseTranspCity = findViewById(R.id.insertTranspCity)

        choseBtn = findViewById(R.id.choseBtn)
        ApplyBtn = findViewById(R.id.ApplyBtn)
        CancelBtn = findViewById(R.id.CancelBtn)
        choseCityBtn = findViewById(R.id.choseCityBtn)
    }

    private fun setTextSize() {
        chooseTransportNum!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        insertTranspTypeText!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        chooseTranspType!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        chooseTranspCity!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()

        choseBtn!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        choseCityBtn!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        ApplyBtn!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
        CancelBtn!!.textSize = ControlVars.CURRENT_TEXT_SIZE.toFloat()
    }
}