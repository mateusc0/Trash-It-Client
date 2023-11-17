package br.com.fiap.trashit.view.components

import android.content.Context
import android.widget.Toast

fun trashItToast(text: String, context: Context) {
    Toast.makeText( context, text, Toast.LENGTH_SHORT).show()
}