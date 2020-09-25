import com.google.gson.annotations.SerializedName




data class Present_addresses (

	@SerializedName("phone_no") val phone_no : String,
	@SerializedName("police_station") val police_station : String,
	@SerializedName("police_station_bn") val police_station_bn : String,
	@SerializedName("post_office") val post_office : String,
	@SerializedName("post_office_bn") val post_office_bn : String,
	@SerializedName("road_word_no") val road_word_no : String,
	@SerializedName("road_word_no_bn") val road_word_no_bn : String,
	@SerializedName("village_house_no") val village_house_no : String,
	@SerializedName("village_house_no_bn") val village_house_no_bn : String,
	@SerializedName("division") val division : Division,
	@SerializedName("district") val district : Distric,
	@SerializedName("upazila") val upazila : Upazila
)
{
	fun g_phone_no(): String {
		if (phone_no.equals(null))
			return ""
		return phone_no!!
	}
	fun g_police_station(): String {
		if (police_station.equals(null))
			return ""
		return police_station!!
	}
	fun g_police_station_bn(): String {
		if (police_station_bn.equals(null))
			return ""
		return police_station_bn!!
	}
	fun g_post_office(): String {
		if (post_office.equals(null))
			return ""
		return post_office!!
	}
	fun g_post_office_bn(): String {
		if (post_office_bn.equals(null))
			return ""
		return post_office_bn!!
	}
	fun g_road_word_no(): String {
	if (road_word_no.equals(null))
		return ""
	return road_word_no!!
}
	fun g_road_word_no_bn(): String {
		if (road_word_no_bn.equals(null))
			return ""
		return road_word_no_bn!!
	}
	fun g_village_house_no(): String {
		if (village_house_no.equals(null))
			return ""
		return village_house_no!!
	}
	fun g_village_house_no_bn(): String {
		if (village_house_no_bn.equals(null))
			return ""
		return village_house_no_bn!!
	}
}