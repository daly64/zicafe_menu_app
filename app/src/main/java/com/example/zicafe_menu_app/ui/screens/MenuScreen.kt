package com.example.zicafe_menu_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.zicafe_menu_app.data.*

// Modern Brown Palette
val CoffeeBrown = Color(0xFF4E342E)
val RichBrown = Color(0xFF5D4037)
val LightSurface = Color(0xFFFAFAFA)
val TextPrimary = Color(0xFF2D2D2D)
val TextSecondary = Color(0xFF757575)
val DividerColor = Color(0xFFE0E0E0)

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MenuScreen(menuData: MenuData) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Smaller Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(brush = Brush.horizontalGradient(listOf(Color.Transparent, CoffeeBrown.copy(alpha = 0.2f))))
                )
                Text(
                    text = "MENU",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = CoffeeBrown,
                        fontSize = 32.sp, // Smaller font
                        fontWeight = FontWeight.ExtraLight,
                        letterSpacing = 10.sp,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f)
                        .background(brush = Brush.horizontalGradient(listOf(CoffeeBrown.copy(alpha = 0.2f), Color.Transparent)))
                )
            }

            // 2x2 Grid Layout
            val categories = menuData.categories
            Column(modifier = Modifier.weight(1f)) {
                // Row 1
                Row(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.weight(1f)) {
                        categories.getOrNull(0)?.let { CategorySection(it) }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        categories.getOrNull(1)?.let { CategorySection(it) }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Row 2
                Row(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.weight(1f)) {
                        categories.getOrNull(2)?.let { CategorySection(it) }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        categories.getOrNull(3)?.let { CategorySection(it) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun CategorySection(category: MenuCategory) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(LightSurface)
            .padding(16.dp)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = CoffeeBrown,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp, // Smaller header
                fontFamily = FontFamily.SansSerif,
                letterSpacing = 1.sp
            )
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Column(modifier = Modifier.fillMaxWidth()) {
            category.items.take(8).forEach { item -> // Limit items to prevent overflow in grid
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
                            fontSize = 18.sp, // Responsive/Smaller font
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.SansSerif,
                            maxLines = 1
                        )
                        if (item.detail != null) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item.detail,
                                color = TextSecondary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = FontFamily.SansSerif,
                                maxLines = 1
                            )
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                            .height(0.5.dp)
                            .background(DividerColor)
                    )

                    Text(
                        text = item.price,
                        color = RichBrown,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
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
