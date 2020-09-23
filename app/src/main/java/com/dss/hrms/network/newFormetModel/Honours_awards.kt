import com.google.gson.annotations.SerializedName



data class Honours_awards (

	@SerializedName("award_title") val award_title : String,
	@SerializedName("award_title_bn") val award_title_bn : String,
	@SerializedName("award_details") val award_details : String,
	@SerializedName("award_details_bn") val award_details_bn : String,
	@SerializedName("award_date") val award_date : String
)