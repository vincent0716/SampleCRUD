package com.vmccn.crud.Tools

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EdtTextChange(val textInputLayout: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s.toString().isEmpty()){
            textInputLayout.isErrorEnabled = true;
            textInputLayout.error = "This is required field"
        }
        else{
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = null
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}