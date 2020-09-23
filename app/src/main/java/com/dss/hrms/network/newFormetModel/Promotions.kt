import com.google.gson.annotations.SerializedName




data class Promotions (

	@SerializedName("memo_no") val memo_no : String,
	@SerializedName("memo_no_bn") val memo_no_bn : String,
	@SerializedName("memo_date") val memo_date : String
)