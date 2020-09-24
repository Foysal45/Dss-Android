import com.google.gson.annotations.SerializedName



data class References (

	@SerializedName("name") val name : String?,
	@SerializedName("name_bn") val name_bn : String?,
	@SerializedName("relation") val relation : String?,
	@SerializedName("relation_bn") val relation_bn : String?,
	@SerializedName("contact_no") val contact_no : String?,
	@SerializedName("contact_no_bn") val contact_no_bn : String?,
	@SerializedName("address") val address : String?,
	@SerializedName("address_bn") val address_bn : String?
){
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
	fun g_relation(): String {
		if (relation.equals(null))
			return ""
		return relation!!
	}
	fun g_relation_bn(): String {
		if (relation_bn.equals(null))
			return ""
		return relation_bn!!
	}
	fun g_contact_no(): String {
		if (contact_no.equals(null))
			return ""
		return contact_no!!
	}
	fun g_contact_no_bn(): String {
		if (contact_no_bn.equals(null))
			return ""
		return contact_no_bn!!
	}
	fun g_address(): String {
		if (address.equals(null))
			return ""
		return address!!
	}
	fun g_address_bn(): String {
		if (address_bn.equals(null))
			return ""
		return address_bn!!
	}
}