package com.razzdrawon.urbandictionary.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.razzdrawon.urbandictionary.R
import com.razzdrawon.urbandictionary.adapter.DefinitionAdapter
import com.razzdrawon.urbandictionary.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var definitionsAdapter = DefinitionAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        definitionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = definitionsAdapter
        }

        obseveViewModel()
    }

    fun obseveViewModel() {


        viewModel.definitions.observe(this, Observer { definitions ->
            definitions?.let {
                definitionList.visibility = View.VISIBLE
                definitionsAdapter.updateDefinitions(it)
            }
        })
        viewModel.definitionsError.observe(this, Observer { isError ->
            isError?.let {
                error_view.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    error_view.visibility = View.GONE
                    definitionList.visibility = View.GONE
                }
            }
        })

        viewModel.contentState.observe(this, Observer {
            (definitionList.adapter as DefinitionAdapter).submitList(it)
            definitionList.requestFocus()
        })

        with(search_view) {

            setOnSearchConfirmedListener { searchView, query ->
                val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    activity!!.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                viewModel.refresh(query)
            }

            setSuggestionsDisabled(false)

            setOnRightBtnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setCancelable(true)
                    .setSingleChoiceItems(
                        arrayOf("Thumbs up", "Thumbs Down", "None"),
                        viewModel.selectedSort.ordinal
                    ) { dialog, index ->
                        dialog.dismiss()
                        viewModel.sortWith(index)
                    }.show()
            }

            showRightButton()
            setRightButtonDrawable(R.drawable.sort)
            isVoiceInputButtonEnabled = false
        }
    }

}
