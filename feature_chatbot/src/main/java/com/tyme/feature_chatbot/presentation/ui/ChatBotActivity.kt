package com.tyme.feature_chatbot.presentation.ui

import com.tyme.feature_chatbot.presentation.viewmodel.ChatBotViewModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tyme.base_feature.common.Result
import com.tyme.feature_chatbot.ChatMessage
import com.tyme.feature_chatbot.databinding.ActivityChatBotBinding
import com.tyme.feature_chatbot.presentation.adapter.ChatAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatBotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBotBinding
    private val viewModel: ChatBotViewModel by viewModel()
    private val messages = ArrayList<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = ChatAdapter(this, messages)  // <-- initialize here
        binding.chatListView.adapter = chatAdapter

        viewModel.receivedMessages.observe(this, { response ->
            when (response) {
                is Result.Success -> {

                    messages[messages.size - 1] = ChatMessage(response.data?:"", false)
                    chatAdapter.notifyDataSetChanged()
                    binding.chatListView.smoothScrollToPosition(chatAdapter.count)

                }
                is Result.Loading -> {

                } else -> {
                    messages[messages.size - 1] = ChatMessage("Timeout. Please send another request", false)
                    chatAdapter.notifyDataSetChanged()
                    binding.chatListView.smoothScrollToPosition(chatAdapter.count)

            }
            }

        })

        binding.messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && s[s.length - 1] == '\n') {
                    sendRequest(s.toString().trim())
                    binding.messageEditText.text.clear()
                }
            }
        })

        binding.messageEditText.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.messageEditText.right - binding.messageEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    val message = binding.messageEditText.text.toString().trim()
                    if (message.isNotEmpty()) {
                        sendRequest(message)
                    }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        binding.microphoneButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                val spokenText = matches[0]
                sendRequest(spokenText)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val VOICE_RECOGNITION_REQUEST_CODE = 1
    }

    private fun sendRequest(prompt: String) {
        messages.add(ChatMessage(prompt,true))
        chatAdapter.notifyDataSetChanged()
        binding.messageEditText.text.clear()

        messages.add(ChatMessage("", false, true))
        chatAdapter.notifyDataSetChanged()
        binding.chatListView.smoothScrollToPosition(chatAdapter.count)
        viewModel.getMessage(prompt)
    }
}
