package com.example.davaleba6

import android.graphics.Color
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.davaleba6.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ageIsValid = false
        var firstnameIsValid = false
        var lastnameIsValid = false
        var emailIsValid = false

        binding.firstname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val input = binding.firstname.text.toString()
                if (input.isNotEmpty()){
                    binding.firstname.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                    binding.tvSuccessFirstname.visibility = View.VISIBLE
                    binding.tvErrorFirstname.visibility = View.GONE
                    firstnameIsValid = true
                }else{
                    binding.firstname.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    binding.tvErrorFirstname.visibility = View.VISIBLE
                    binding.tvSuccessFirstname.visibility = View.GONE

                }
            }
        })


        binding.lastname.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val input = binding.lastname.text.toString()
                if (input.isNotEmpty()){
                    binding.lastname.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                    binding.tvSuccessLastname.visibility = View.VISIBLE
                    binding.tvErrorLastname.visibility = View.GONE
                    lastnameIsValid = true
                }else{
                    binding.firstname.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    binding.tvErrorLastname.visibility = View.VISIBLE
                    binding.tvSuccessLastname.visibility = View.GONE

                }
            }
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val input = binding.etEmail.text.toString()
                if (input.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()){
                    binding.etEmail.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                    binding.tvSuccessEmail.visibility = View.VISIBLE
                    binding.tvErrorEmail.visibility = View.GONE
                    emailIsValid = true
                }else{
                    binding.etEmail.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    binding.tvErrorEmail.visibility = View.VISIBLE
                    binding.tvSuccessEmail.visibility = View.GONE

                }
            }
        })

        binding.etAge.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val input = binding.etAge.text.toString()

                try {
                    val inputAsInt = input.toInt()
                    if (inputAsInt < 0 || inputAsInt > 130){

                        binding.etAge.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                        binding.tvErrorAge.visibility = View.VISIBLE
                        binding.tvSuccessAge.visibility = View.GONE
                    }else{
                        binding.etAge.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.green))
                        binding.tvSuccessAge.visibility = View.VISIBLE
                        binding.tvErrorAge.visibility = View.GONE
                        ageIsValid = true
                    }

                }catch (e:NumberFormatException){
                    binding.etAge.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                    binding.tvErrorAge.visibility = View.VISIBLE
                    binding.tvSuccessAge.visibility = View.GONE
                }
            }
        })


        var activeUser = 0
        binding.btnAdduser.setOnClickListener{

            val firstName = binding.firstname.text.toString()
            val lastName = binding.lastname.text.toString()
            val email = binding.etEmail.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?:0



            val newUser = User(firstName,lastName,age,email)


            if (userExists(newUser)){
                binding.tvUserExists.visibility = View.VISIBLE
                binding.tvUserAdded.visibility = View.GONE
                binding.userUpdated.visibility = View.GONE
            }else if (ageIsValid && firstnameIsValid && emailIsValid && lastnameIsValid){
                addUser(firstName,lastName,age,email)
                activeUser++
                 binding.etActiveUsers.text = activeUser.toString()
                // Log.d("UserAdded", "User Added: ${addUser(firstName,lastName,age,email)}")
                Log.d("UserAdded", "User Added: ${userList}")
                binding.tvUserAdded.visibility = View.VISIBLE
                binding.tvUserExists.visibility = View.GONE
                binding.userUpdated.visibility = View.GONE

            }

        }
        var removeUser = 0

        binding.btnRemoveuser.setOnClickListener{

            val firstName = binding.firstname.text.toString()
            val lastName = binding.lastname.text.toString()
            val email = binding.etEmail.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?:0

            val newUser = User(firstName,lastName,age,email)
            if (userExists(newUser)){
                removeUser(email)
                removeUser++
                activeUser--
                binding.etActiveUsers.text = activeUser.toString()
                binding.etDeleteUser.text = removeUser.toString()
                binding.tvUserNotExists.visibility = View.GONE
                binding.tvUserDelete.visibility = View.VISIBLE
                binding.userUpdated.visibility = View.GONE
                Log.d("UserAdded", "User Added: ${userList}")
            }else{
                binding.tvUserDelete.visibility = View.GONE
                binding.tvUserNotExists.visibility = View.VISIBLE
                binding.userUpdated.visibility = View.GONE
            }

        }

        binding.btnUpdateUser.setOnClickListener{
            val firstName = binding.firstname.text.toString()
            val lastName = binding.lastname.text.toString()
            val email = binding.etEmail.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull() ?:0

            var user = User(firstName,lastName,age,email)
            if(userExists(user)){
                updateUser(email,firstName,lastName)
                Log.d("UserAdded", "User Added: ${userList}")
                binding.userUpdated.visibility = View.VISIBLE
            }
        }




    }

    private val userList = mutableListOf<User>()

    private fun addUser(firstName: String, lastName: String, age: Int, email: String) {
        val newUser = User(firstName, lastName, age, email)
        userList.add(newUser)
    }

    private fun userExists(user: User): Boolean{
        return userList.any {it.email == user.email}
    }

    private fun removeUser(email: String ) {
        val userToRemove = userList.find { it.email == email  }

        if (userToRemove != null) {
            userList.remove(userToRemove)
        }
    }

    private fun updateUser(email: String, firstName: String, lastName: String) {
        val userToUpdate = userList.find { it.email == email }

        if (userToUpdate != null) {

            userList.remove(userToUpdate)

            val gmailPattern = "@gmail.com"
            userToUpdate.firstName = firstName.uppercase()
            userToUpdate.lastName = lastName.uppercase()
            userToUpdate.email = firstName.uppercase() + lastName.uppercase() + gmailPattern
            userToUpdate.age++

            userList.add(userToUpdate)
        }
    }

}