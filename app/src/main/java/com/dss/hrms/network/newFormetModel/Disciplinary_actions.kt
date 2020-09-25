import com.google.gson.annotations.SerializedName


data class Disciplinary_actions(

	@SerializedName("disciplinary_action_details") val disciplinary_action_details: String?,
	@SerializedName("disciplinary_action_details_bn") val disciplinary_action_details_bn: String?,
	@SerializedName("judgment") val judgment: String?,
	@SerializedName("judgment_bn") val judgment_bn: String?,
	@SerializedName("final_judgment") val final_judgment: String?,
	@SerializedName("final_judgment_bn") val final_judgment_bn: String?,
	@SerializedName("remarks") val remarks: String?,
	@SerializedName("remarks_bn") val remarks_bn: String?,
	@SerializedName("date") val date: String?,
	@SerializedName("disciplinary_action_category") val disciplinary_action_category: Disciplinary_action_category
) {
    fun g_disciplinary_action_details(): String {
        if (disciplinary_action_details.equals(null))
            return ""
        return disciplinary_action_details!!
    }

    fun g_disciplinary_action_details_bn(): String {
        if (disciplinary_action_details_bn.equals(null))
            return ""
        return disciplinary_action_details_bn!!
    }

    fun g_judgment(): String {
        if (judgment.equals(null))
            return ""
        return judgment!!
    }

    fun g_judgment_bn(): String {
        if (judgment_bn.equals(null))
            return ""
        return judgment_bn!!
    }

    fun g_final_judgment(): String {
        if (final_judgment.equals(null))
            return ""
        return final_judgment!!
    }

    fun g_final_judgment_bn(): String {
        if (final_judgment_bn.equals(null))
            return ""
        return final_judgment_bn!!
    }

    fun g_remarks(): String {
        if (remarks.equals(null))
            return ""
        return remarks!!
    }

    fun g_remarks_bn(): String {
        if (remarks_bn.equals(null))
            return ""
        return remarks_bn!!
    }

    fun g_date(): String {
        if (date.equals(null))
            return ""
        return date!!
    }
}