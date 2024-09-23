package com.example.seebuses.core.data

import com.example.seebuses.core.entities.BlockElement
import com.example.seebuses.tables.Cities
import com.example.seebuses.tables.MetroCities

object CoreData {

    var transports: Array<BlockElement?> = arrayOfNulls(ControlVars.DEFAULT_BLOCKS_COUNT)
    val ct: List<Array<String>> =
        if (ControlVars.IS_RUSSIAN) Cities.initTable_ru() else Cities.initTable_en()
    val mc: List<Array<String>> =
        if (ControlVars.IS_RUSSIAN) MetroCities.initTable_ru() else MetroCities.initTable_en()
}