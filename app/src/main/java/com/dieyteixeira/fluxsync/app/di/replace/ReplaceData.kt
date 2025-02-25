package com.dieyteixeira.fluxsync.app.di.replace

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.GolfCourse
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.material.icons.filled.Kayaking
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.graphics.toColorInt

fun iconToString(icon: ImageVector): String {
    return when (icon) {
        Icons.Default.Fastfood -> "Fastfood"
        Icons.Default.AutoStories -> "AutoStories"
        Icons.Default.Kayaking -> "Kayaking"
        Icons.Default.Handshake -> "Handshake"
        Icons.Default.DirectionsCar -> "DirectionsCar"
        Icons.Default.Apps -> "Apps"
        Icons.Default.AccountBalance -> "AccountBalance"
        Icons.Default.Build -> "Build"
        Icons.Default.Bluetooth -> "Bluetooth"
        Icons.Default.Campaign -> "Campaign"
        Icons.Default.Coffee -> "Coffee"
        Icons.Default.Computer -> "Computer"
        Icons.Default.Create -> "Create"
        Icons.Default.DirectionsBike -> "DirectionsBike"
        Icons.Default.Done -> "Done"
        Icons.Default.Edit -> "Edit"
        Icons.Default.Email -> "Email"
        Icons.Default.Explore -> "Explore"
        Icons.Default.Favorite -> "Favorite"
        Icons.Default.FitnessCenter -> "FitnessCenter"
        Icons.Default.GolfCourse -> "GolfCourse"
        Icons.Default.HolidayVillage -> "HolidayVillage"
        Icons.Default.Home -> "Home"
        Icons.Default.Hotel -> "Hotel"
        Icons.Default.ImportContacts -> "ImportContacts"
        Icons.Default.LocalTaxi -> "LocalTaxi"
        Icons.Default.LibraryBooks -> "LibraryBooks"
        Icons.Default.LocalDining -> "LocalDining"
        Icons.Default.Loyalty -> "Loyalty"
        Icons.Default.Map -> "Map"
        Icons.Default.Mic -> "Mic"
        Icons.Default.MonetizationOn -> "MonetizationOn"
        Icons.Default.Mood -> "Mood"
        Icons.Default.MusicNote -> "MusicNote"
        else -> "Outros"
    }
}

fun stringToIcon(iconString: String): ImageVector {
    return when (iconString) {
        "Fastfood" -> Icons.Default.Fastfood
        "AutoStories" -> Icons.Default.AutoStories
        "Kayaking" -> Icons.Default.Kayaking
        "Handshake" -> Icons.Default.Handshake
        "DirectionsCar" -> Icons.Default.DirectionsCar
        "Apps" -> Icons.Default.Apps
        "AccountBalance" -> Icons.Default.AccountBalance
        "Build" -> Icons.Default.Build
        "Bluetooth" -> Icons.Default.Bluetooth
        "Campaign" -> Icons.Default.Campaign
        "Coffee" -> Icons.Default.Coffee
        "Computer" -> Icons.Default.Computer
        "Create" -> Icons.Default.Create
        "DirectionsBike" -> Icons.Default.DirectionsBike
        "Done" -> Icons.Default.Done
        "Edit" -> Icons.Default.Edit
        "Email" -> Icons.Default.Email
        "Explore" -> Icons.Default.Explore
        "Favorite" -> Icons.Default.Favorite
        "FitnessCenter" -> Icons.Default.FitnessCenter
        "GolfCourse" -> Icons.Default.GolfCourse
        "HolidayVillage" -> Icons.Default.HolidayVillage
        "Home" -> Icons.Default.Home
        "Hotel" -> Icons.Default.Hotel
        "ImportContacts" -> Icons.Default.ImportContacts
        "LocalTaxi" -> Icons.Default.LocalTaxi
        "LibraryBooks" -> Icons.Default.LibraryBooks
        "LocalDining" -> Icons.Default.LocalDining
        "Loyalty" -> Icons.Default.Loyalty
        "Map" -> Icons.Default.Map
        "Mic" -> Icons.Default.Mic
        "MonetizationOn" -> Icons.Default.MonetizationOn
        "Mood" -> Icons.Default.Mood
        "MusicNote" -> Icons.Default.MusicNote
        else -> Icons.Default.Apps
    }
}

fun colorToString(color: Color): String {
    return "#${Integer.toHexString(color.toArgb())}"
}

fun stringToColor(colorString: String): Color {
    return Color(colorString.toColorInt())
}