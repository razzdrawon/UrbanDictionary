package com.razzdrawon.urbandictionary.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
                error_view.visibility = if(it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    error_view.visibility = View.GONE
                    definitionList.visibility = View.GONE
                }
            }
        })

        with(search_view) {

            setOnSearchConfirmedListener { searchView, query ->
                // Handle a search confirmation. This is the place where you'd
                // want to perform a search against your data provider.
                viewModel.refresh(query)

            }

            setSuggestionsDisabled(false)

            showRightButton()
            setRightButtonDrawable(R.drawable.sort)

            setVoiceInputButtonEnabled(false)
        }
    }

}
