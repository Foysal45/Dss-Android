import com.google.gson.annotations.SerializedName




data class Promotions (

	@SerializedName("memo_no") val memo_no : String,
	@SerializedName("memo_no_bn") val memo_no_bn : String,
	@SerializedName("memo_date") val memo_date : String,
	@SerializedName("previous_posts") val previous_posts : Previous_posts,
	@SerializedName("current_position") val current_position : Current_position
){
	fun g_memo_no(): String {
		if (memo_no.equals(null))
			return ""
		return memo_no!!
	}

	fun g_memo_no_bn(): String {
		if (memo_no_bn.equals(null))
			return ""
		return memo_no_bn!!
	}
	fun g_memo_date(): String {
		if (memo_date.equals(null))
			return ""
		return memo_date!!
	}

}