import com.google.gson.annotations.SerializedName


data class Educational_qualifications(
    @SerializedName("name_of_degree") val name_of_degree: String?,
    @SerializedName("name_of_degree_bn") val name_of_degree_bn: String?,
    @SerializedName("name_of_institute") val name_of_institute: String?,
    @SerializedName("name_of_institute_bn") val name_of_institute_bn: String?,
    @SerializedName("board_university") val board_university: String?,
    @SerializedName("board_university_bn") val board_university_bn: String?,
    @SerializedName("passing_year") val passing_year: String?,
    @SerializedName("division_cgpa") val division_cgpa: String?
) {
    fun g_name_of_degree(): String {
        if (name_of_degree.equals(null))
            return ""
        return name_of_degree!!
    }

    fun g_name_of_degree_bn(): String {
        if (name_of_degree_bn.equals(null))
            return ""
        return name_of_degree_bn!!
    }

    fun g_name_of_institute(): String {
        if (name_of_institute.equals(null))
            return ""
        return name_of_institute!!
    }

    fun g_name_of_institute_bn(): String {
        if (name_of_institute_bn.equals(null))
            return ""
        return name_of_institute_bn!!
    }

    fun g_board_university(): String {
        if (board_university.equals(null))
            return ""
        return board_university!!
    }
    fun g_passing_year(): String {
        if (passing_year.equals(null))
            return ""
        return passing_year!!
    }
    fun g_division_cgpa(): String {
        if (division_cgpa.equals(null))
            return ""
        return division_cgpa!!
    }
}