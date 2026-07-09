package com.example.zicafe_menu_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.zicafe_menu_app.data.*

// Modern Brown Palette (Reverted to previous)
val CoffeeBrown = Color(0xFF4E342E)
val RichBrown = Color(0xFF5D4037)
val LightSurface = Color(0xFFFAFAFA)
val TextPrimary = Color(0xFF2D2D2D)
val TextSecondary = Color(0xFF757575)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MenuScreen(menuData: MenuData) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val categories = menuData.categories

    // Responsive scaling - maintaining visibility but with cleaner weights
    val categoryCount = categories.size.coerceAtLeast(1)
    
    val titleSize = (screenHeight * 0.085f).sp
    val headerSize = (screenHeight * 0.05f).sp
    val itemSize = (screenHeight * 0.032f).sp
    val detailSize = (screenHeight * 0.02f).sp
    val paddingSize = (screenWidth * 0.015f).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingSize)
        ) {
            // Reverted Minimalist Header Style
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingSize),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.height(1.dp).weight(1f).background(CoffeeBrown.copy(alpha = 0.2f)))
                Text(
                    text = "MENU",
                    modifier = Modifier.padding(horizontal = paddingSize * 2),
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = CoffeeBrown,
                        fontSize = titleSize,
                        fontWeight = FontWeight.ExtraLight, // Reverted to minimalist weight
                        letterSpacing = (titleSize.value * 0.4f).sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Box(modifier = Modifier.height(1.dp).weight(1f).background(CoffeeBrown.copy(alpha = 0.2f)))
            }

            // Adaptive Multi-Column Layout
            Row(modifier = Modifier.fillMaxSize()) {
                val itemsPerColumn = if (categoryCount <= 3) 1 else (categoryCount + 1) / 2
                val columns = categories.chunked(itemsPerColumn)

                columns.forEachIndexed { colIndex, colCategories ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        colCategories.forEachIndexed { catIndex, category ->
                            Box(modifier = Modifier.weight(1f)) {
                                CategorySection(
                                    category = category,
                                    modifier = Modifier.fillMaxSize(),
                                    headerSize = headerSize,
                                    itemSize = itemSize,
                                    detailSize = detailSize
                                )
                            }
                            if (catIndex < colCategories.size - 1) {
                                Spacer(modifier = Modifier.height(paddingSize))
                            }
                        }
                    }
                    if (colIndex < columns.size - 1) {
                        Spacer(modifier = Modifier.width(paddingSize))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CategorySection(
    category: MenuCategory, 
    modifier: Modifier = Modifier,
    headerSize: TextUnit,
    itemSize: TextUnit,
    detailSize: TextUnit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(LightSurface)
            .padding(16.dp)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = CoffeeBrown,
                fontWeight = FontWeight.Bold, // Reverted to clean bold
                fontSize = headerSize,
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 1.5.sp
            )
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Normal list style (Top alignment)
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            category.items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = item.name,
                            color = TextPrimary,
                            fontSize = itemSize,
                            fontWeight = FontWeight.Normal, // Reverted to clean normal weight
                            fontFamily = FontFamily.SansSerif,
                            maxLines = 1
                        )
                        if (item.detail != null) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = item.detail,
                                color = TextSecondary,
                                fontSize = detailSize,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.SansSerif,
                                maxLines = 1
                            )
                        }
                    }

                    Text(
                        text = item.price,
                        color = RichBrown,
                        fontSize = itemSize,
                        fontWeight = FontWeight.SemiBold, // Reverted to clean semi-bold
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

val sampleMenuData = MenuData(
    categories = listOf(
        MenuCategory(
            name = "CAFÉ",
            items = listOf(
                MenuItem("Expresse", "2.000"),
                MenuItem("Demi", "2.000"),
                MenuItem("Allonge", "2.000"),
                MenuItem("Americain", "2.000"),
                MenuItem("Cappuccino", "2.500"),
                MenuItem("Direct", "2.500"),
                MenuItem("Direct Special", "3.000"),
                MenuItem("supplement sirop", "0.500")
            )
        ),
        MenuCategory(
            name = "BOISSONS",
            items = listOf(
                MenuItem("Eau Minerale", "1.500", "1.0l"),
                MenuItem("Eau Minerale", "1.000", "0.5l"),
                MenuItem("Canette", "2.500"),
                MenuItem("Jus de fruit", "3.000"),
                MenuItem("Thé Vert", "2.000")
            )
        ),
        MenuCategory(
            name = "VIENNOISERIES",
            items = listOf(
                MenuItem("Gateau", "3.000"),
                MenuItem("Cake", "2.000")
            )
        )
    )
)
