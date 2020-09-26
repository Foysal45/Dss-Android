import com.google.gson.annotations.SerializedName


data class Spouses(
	@SerializedName("name") val name: String?,
	@SerializedName("name_bn") val name_bn: String?,
	@SerializedName("email_address") val email_address: String?,
	@SerializedName("occupation") val occupation: String?,
	@SerializedName("occupation_bn") val occupation_bn: String?,
	@SerializedName("phone_no") val phone_no: String?,
	@SerializedName("phone_no_bn") val phone_no_bn: String?,
	@SerializedName("mobile_no") val mobile_no: String?,
	@SerializedName("mobile_no_bn") val mobile_no_bn: String?,
	@SerializedName("distric") val distric: Distric,
	@SerializedName("religion") val religion: Religion
) {
    fun g_name(): String {
        if (name.equals(null))
            return ""
        return name!!
    }

    fun g_name_bn(): String {
        if (name_bn.equals(null))
            return ""
        return name_bn!!
    }

    fun g_email_address(): String {
        if (email_address.equals(null))
            return ""
        return email_address!!
    }

    fun g_occupation(): String {
        if (occupation.equals(null))
            return ""
        return occupation!!
    }

    fun g_occupation_bn(): String {
        if (occupation_bn.equals(null))
            return ""
        return occupation_bn!!
    }

    fun g_phone_no(): String {
        if (phone_no.equals(null))
            return ""
        return phone_no!!
    }

    fun g_phone_no_bn(): String {
        if (phone_no_bn.equals(null))
            return ""
        return phone_no_bn!!
    }

    fun g_mobile_no(): String {
        if (mobile_no.equals(null))
            return ""
        return mobile_no!!
    }

    fun g_mobile_no_bn(): String {
        if (mobile_no_bn.equals(null))
            return ""
        return mobile_no_bn!!
    }



}
