package com.andef.crosszero.data.datasource

import com.andef.crosszero.domain.entities.CellSign
import com.andef.crosszero.domain.entities.Field

object FieldData {
    private fun getClearField(): Field {
        val field = ArrayList<ArrayList<CellSign>>()
        for (i in 0..2) {
            val tmp = ArrayList<CellSign>()
            for (j in 0..2) {
                tmp.add(CellSign.EMPTY)
            }
            field.add(tmp)
        }
        return Field(field)
    }

    val newClearField
        get() = getClearField()
}