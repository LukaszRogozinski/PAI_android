package com.example.pai.features.products.edit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.pai.R
import com.example.pai.databinding.EditProductFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class EditProductFragment : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val a = p0?.getItemAtPosition(p2)
        Log.i("TAG", "fas")
    }


    private lateinit var binding: EditProductFragmentBinding

    private val viewModel: EditProductViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, EditProductViewModel.Factory(activity.application))
            .get(EditProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_product_fragment,
            container,
            false
        )
        binding.vm = viewModel
        binding.warehouseTypeSpinner.visibility = View.VISIBLE

        viewModel.loadWarehouses()

//        val categories = arrayListOf("Automobile","Travel","Personal","Education","Computers","Business Services" )
//
//        val dataAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categories)
//        binding.warehouseTypeSpinner.adapter = dataAdapter
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.isWarehousesLoaded.observe(this, Observer {
//////            binding.warehouseTypeSpinner.visibility = View.VISIBLE
////            viewModel.isWarehousesLoadedFinish()
////        })

        createArrayAdapterForSpinner(viewModel.warehouses, binding.warehouseTypeSpinner)
        createArrayAdapterForSpinner(viewModel.status, binding.statusSpinner)
        createArrayAdapterForSpinner(viewModel.productTypes, binding.productTypeSpinner)


//        ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item,
//            viewModel.warehouses
//        ).also {
//                adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            binding.warehouseTypeSpinner.adapter = adapter
//        }
//        binding.warehouseTypeSpinner.onItemSelectedListener = this
    }

    private fun <T> createArrayAdapterForSpinner(objects: List<T>, spinner: Spinner){
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            objects
        ).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

}
