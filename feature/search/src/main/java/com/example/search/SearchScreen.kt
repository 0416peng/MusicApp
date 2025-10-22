package com.example.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui.SearchTextField

@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(),
                 onSearchDetailClick: (keyword:String) -> Unit
                 ){
    val searchText by viewModel.searchText.collectAsState()
    val hotSearchData by viewModel.hotSearchData.collectAsState()
    val searchSuggestData by viewModel.searchSuggestData.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getHotSearchData()
    }
    Column {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)){
            SearchTextField(value=searchText,
                onValueChange = {newText->
                    viewModel.onSearchTextChanged(newText)
                },
                onSearch = {keyword->
                    onSearchDetailClick(keyword)
                },
                hint = "搜索"
                )
        }
        if (searchSuggestData==null||searchText==""){
        Card(modifier = Modifier
            .padding(36.dp)
            .fillMaxSize(),
            colors= CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {
            Text("热搜榜",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
                )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.3f)
            )

            if(hotSearchData!=null){
                LazyColumn {
                    itemsIndexed(hotSearchData!!.result.hots){
                        index,item->
                       val color= if(index<=2) Color.Red else Color.Black
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable{onSearchDetailClick(item.first)},
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            Text("${index+1}",
                                modifier=Modifier.padding(16.dp),
                                color=color
                                )
                            Text(
                                item.first,

                                )
                        }
                    }
                }
        }
    }
}else if(searchSuggestData!=null&&searchSuggestData!!.code==200){
    LazyColumn {
        items(searchSuggestData!!.result.allMatch){
            item->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(item.keyword,
                    modifier=Modifier.padding(12.dp)
                        .clickable{onSearchDetailClick(item.keyword)}
                    )
            }
        }
    }
}

    }}