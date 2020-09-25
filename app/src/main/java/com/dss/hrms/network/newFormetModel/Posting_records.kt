import com.google.gson.annotations.SerializedName




data class Posting_records (

	@SerializedName("effective_date") val effective_date : String?,
	@SerializedName("transfer_type") val transfer_type : Transfer_type,
	@SerializedName("transfer_from") val transfer_from : Transfer_from,
	@SerializedName("transfer_to") val transfer_to : Transfer_to
)
{
	fun g_effective_date(): String {
		if (effective_date.equals(null))
			return ""
		return effective_date!!
	}
}