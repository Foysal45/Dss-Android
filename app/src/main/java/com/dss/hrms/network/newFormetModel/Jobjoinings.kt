import com.google.gson.annotations.SerializedName


data class Jobjoinings(

	@SerializedName("joining_date") val joining_date: String,
	@SerializedName("pension_date") val pension_date: String,
	@SerializedName("prl_date") val prl_date: String,
	@SerializedName("office") val office: Office,
	@SerializedName("designation") val designation: Designation,
	@SerializedName("department") val department: Department,
	@SerializedName("job_type") val job_type: Job_type,
	@SerializedName("employee_class") val employee_class: Employee_class,
	@SerializedName("grade") val grade: Grade
) {
    fun g_joining_date(): String {
        if (joining_date.equals(null))
            return ""
        return joining_date!!
    }

    fun g_pension_date(): String {
        if (pension_date.equals(null))
            return ""
        return pension_date!!
    }

    fun g_prl_date(): String {
        if (prl_date.equals(null))
            return ""
        return prl_date!!
    }


}