import com.google.gson.annotations.SerializedName


data class Official_residentials(

	@SerializedName("memo_no") val memo_no: String?,
	@SerializedName("memo_no_bn") val memo_no_bn: String?,
	@SerializedName("memo_date") val memo_date: String?,
	@SerializedName("office_zone") val office_zone: String?,
	@SerializedName("location") val location: String?,
	@SerializedName("quarter_name") val quarter_name: String?,
	@SerializedName("flat_no_flat_type") val flat_no_flat_type: String?,
	@SerializedName("designation") val designation: Designation
) {
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

    fun g_office_zone(): String {
        if (office_zone.equals(null))
            return ""
        return office_zone!!
    }

    fun g_location(): String {
        if (location.equals(null))
            return ""
        return location!!
    }

    fun g_quarter_name(): String {
        if (quarter_name.equals(null))
            return ""
        return quarter_name!!
    }

    fun g_flat_no_flat_type(): String {
        if (flat_no_flat_type.equals(null))
            return ""
        return flat_no_flat_type!!
    }
}