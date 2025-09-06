package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface{
                    ArtSpaceApp()
                }
            }
        }
    }
}

class RangeRegulator(
    private val state: MutableState<Int>,
    private val maxValue: Int,
    private val minValue: Int
): ReadWriteProperty<Any?, Int> {
    val step = maxValue - minValue + 1

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return state.value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        state.value = value

        if (state.value > maxValue) {
            while (state.value > maxValue) {
                state.value -= step
            }
        } else if(state.value < minValue) {
            while (state.value < minValue) {
                state.value += step
            }
        }
    }

}

@Composable
fun ArtSpaceApp(
    modifier: Modifier = Modifier
) {
    ArtWorkLayout(modifier = modifier)
}

@Preview(showBackground = true,
    widthDp = 600,
    heightDp = 980)
@Composable
fun ArtWorkLayout(
    modifier: Modifier = Modifier
) {
    var indexOfArtWork by RangeRegulator(
        state = remember {
            mutableStateOf(0)
        },
        minValue = 0,
        maxValue = 4
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.25f))
        ArtWorkImage(
            painterId = when(indexOfArtWork) {
                0 -> R.drawable.beast_eared_girl
                1 -> R.drawable.girl_on_bed_taking_off_shoe
                2 -> R.drawable.straight
                3 -> R.drawable.on_bed
                4 -> R.drawable.taking_picture
                else -> R.drawable.ic_launcher_background
            },
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(0.3f))
        ArtWorkInfo(
            titleFontSize = 32,
            authorFontSize = 20,
            modifier = Modifier.padding(4.dp),
            titleText = when(indexOfArtWork) {
                0 -> "蓝丝萦绕的兽耳少女"
                1 -> "居家温柔时光"
                2 -> "镜中 cos 掠影"
                3 -> "床畔纯净之姿"
                4 -> "粉色空间的cosplay"
                 else -> "Android"
            },
            authorText = when(indexOfArtWork) {
                0 -> "浅羽绘里"
                1 -> "林绘梦"
                2 -> "浅野小羽"
                3 -> "苏清然"
                4 -> "星野奈奈"
                else -> "Android"
            }
        )
        Spacer(modifier = Modifier.weight(0.1f))
        ArtWorkNavigateButtoms(
            modifier = Modifier.padding(20.dp),
            onPreviousClicked = {
                indexOfArtWork -= 1
//                if (indexOfArtWork < 0) {
//                    indexOfArtWork += maxNumOfArtWorks
//                }
            },
            onNextClicked = {
                indexOfArtWork += 1
//                if(indexOfArtWork  >= maxNumOfArtWorks) {
//                    indexOfArtWork -= maxNumOfArtWorks
//                }
            }
        )
    }
}

@Composable
fun ArtWorkImage(
    @DrawableRes painterId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = painterId),
        contentDescription = null,
        modifier = modifier
            .shadow(elevation = 4.dp)
            .padding(28.dp)
    )
}

@Composable
fun ArtWorkInfo(
    modifier: Modifier = Modifier,
    titleFontSize: Int,
    authorFontSize: Int,
    titleText: String,
    authorText: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .background(color = colorResource(id = R.color.blue_1))
            .padding(12.dp)
    ) {
        Text(
            text = titleText,
            fontWeight = FontWeight.Light,
            fontSize = titleFontSize.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = authorText,
            fontWeight = FontWeight.Bold,
            fontSize = authorFontSize.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ArtWorkNavigateButtoms(
    modifier: Modifier = Modifier.padding(16.dp),
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            onPreviousClicked()
        },
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
            ) {
            Text(text = "Previous")
        }
        Button(onClick = {
            onNextClicked()
        },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
            ) {
            Text(text = "Next")
        }
    }
}

