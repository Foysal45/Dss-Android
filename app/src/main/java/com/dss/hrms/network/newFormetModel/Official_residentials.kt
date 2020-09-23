import com.google.gson.annotations.SerializedName




data class Official_residentials (

	@SerializedName("memo_no") val memo_no : String,
	@SerializedName("memo_no_bn") val memo_no_bn : String,
	@SerializedName("memo_date") val memo_date : String,
	@SerializedName("office_zone") val office_zone : String,
	@SerializedName("location") val location : String,
	@SerializedName("quarter_name") val quarter_name : String,
	@SerializedName("flat_no_flat_type") val flat_no_flat_type : String,
	@SerializedName("designation") val designation : Designation
)