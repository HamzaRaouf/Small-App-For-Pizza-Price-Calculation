package com.stubshq.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var deliveryRequired = false
    var basePrice = 5
    var topping = 3
    var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val regularText = findViewById<TextView>(R.id.regularText)
        val familyText = findViewById<TextView>(R.id.familyText)
        val noText = findViewById<TextView>(R.id.noText)
        val yesText = findViewById<TextView>(R.id.yesText)
        val toppinText = findViewById<TextView>(R.id.tvimsakAdjustTime)
        val distance = findViewById<EditText>(R.id.distance)

        val regularPizza = findViewById<ConstraintLayout>(R.id.playListSection)
        val familyPizza = findViewById<ConstraintLayout>(R.id.reviewSectin)


        val addToping = findViewById<ImageView>(R.id.imsakAdd)
        val removeToping = findViewById<ImageView>(R.id.imsakMinus)

        val nodelivery = findViewById<ConstraintLayout>(R.id.noDelivery)
        val requireddelivery = findViewById<ConstraintLayout>(R.id.yesDelivery)
        val resulT = findViewById<CardView>(R.id.calculate)
        val distanceCardView = findViewById<CardView>(R.id.destanceCard)

        familyPizza.setOnClickListener(View.OnClickListener { view ->
            familyPizza.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            regularPizza.setBackgroundColor(getResources().getColor(R.color.lecture_card_bg))
            regularText.setTextColor(resources.getColor(R.color.black))
            familyText.setTextColor(resources.getColor(R.color.white))
            basePrice = 8

        })
        regularPizza.setOnClickListener(View.OnClickListener { view ->
            familyPizza.setBackgroundColor(getResources().getColor(R.color.lecture_card_bg))
            regularPizza.setBackgroundColor(getResources().getColor(R.color.colorPrimary))
            regularText.setTextColor(resources.getColor(R.color.white))
            familyText.setTextColor(resources.getColor(R.color.black))
            basePrice = 5

        })

        addToping.setOnClickListener(View.OnClickListener { view ->
            topping = topping +1
            toppinText.text = topping.toString()
        })
        removeToping.setOnClickListener(View.OnClickListener { view ->
            if(topping > 3){
                topping = topping - 1
                toppinText.text = topping.toString()
            }
        })


        nodelivery.setOnClickListener(View.OnClickListener { view ->
           nodelivery.setBackgroundColor(resources.getColor(R.color.colorPrimary))
           requireddelivery.setBackgroundColor(resources.getColor(R.color.lecture_card_bg))
           noText.setTextColor(resources.getColor(R.color.white))
           yesText.setTextColor(resources.getColor(R.color.black))
            distanceCardView.visibility = View.GONE
            deliveryRequired = false
        })

        requireddelivery.setOnClickListener(View.OnClickListener { view ->
            requireddelivery.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            nodelivery.setBackgroundColor(resources.getColor(R.color.lecture_card_bg))
            yesText.setTextColor(resources.getColor(R.color.white))
            noText.setTextColor(resources.getColor(R.color.black))
            distanceCardView.visibility = View.VISIBLE
            deliveryRequired = true
        })

        // Alert UI ETC
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView: View = inflater.inflate(R.layout.result_alert, null)
        builder.setView(dialogView)
        val title = dialogView.findViewById<TextView>(R.id.tvTitle)
        val message = dialogView.findViewById<TextView>(R.id.tvBody)
        val playBtn = dialogView.findViewById<TextView>(R.id.playBtn)
        title.text = "Result"


        val alert = builder.create()
        alert.setCancelable(false)
        playBtn.setOnClickListener { view: View? ->
            alert.dismiss()
        }

        resulT.setOnClickListener(View.OnClickListener {view ->
            if (deliveryRequired) {
                val inputValue = distance.text.toString()
                if (inputValue.isNullOrEmpty()){
                    Toast.makeText(this, "Distance is required", Toast.LENGTH_LONG).show()
                } else {
                    if(inputValue.toInt() <= 5) {
                        total = (basePrice + (topping - 3) + 10).toDouble()
                    } else if(inputValue.toInt() > 5 && inputValue.toInt() <= 10){
                        total = basePrice + (topping - 3) + 10 + ((inputValue.toInt() - 5)*2.5)
                    } else if(inputValue.toInt() > 10) {
                        total = basePrice + (topping - 3) + 25 + ((inputValue.toInt() - 10)*4.0)
                    }

                    message.text = "Total Cost = "+total+" $"
                    alert.window!!.setBackgroundDrawableResource(R.color.transparent)
                    alert.show()
                }



            } else {
                total = (basePrice + (topping - 3)).toDouble()
                message.text = "Total Cost = "+total+" $"
                alert.window!!.setBackgroundDrawableResource(R.color.transparent)
                alert.show()
            }

        })
    }

    
}