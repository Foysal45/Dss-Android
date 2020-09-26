import com.google.gson.annotations.SerializedName



data class Data (
	@SerializedName("name") val name : String?,
	@SerializedName("name_bn") val name_bn : String?,
	@SerializedName("photo") val photo : String?,
	@SerializedName("date_of_birth") val date_of_birth : String?,
	@SerializedName("fathers_name") val fathers_name : String?,
	@SerializedName("fathers_name_bn") val fathers_name_bn : String?,
	@SerializedName("mothers_name") val mothers_name : String?,
	@SerializedName("mothers_name_bn") val mothers_name_bn : String?,
	@SerializedName("promotions") val promotions : List<Promotions>,
	@SerializedName("references") val references : List<References>,
	@SerializedName("gender") val gender : Gender,
	@SerializedName("posting_records") val posting_records : List<Posting_records>,
	//check from
	@SerializedName("jobjoinings") val jobjoinings : List<Jobjoinings>,
	@SerializedName("present_addresses") val present_addresses : List<Present_addresses>,
	@SerializedName("permanent_addresses") val permanent_addresses : List<Permanent_addresses>,
	@SerializedName("educational_qualifications") val educational_qualifications : List<Educational_qualifications>,
	@SerializedName("childs") val childs : List<Childs>,
	@SerializedName("languages") val languages : List<Languages>,
	@SerializedName("local_trainings") val local_trainings : List<Local_trainings>,
	@SerializedName("foreigntrainings") val foreigntrainings : List<Foreigntrainings>,
	@SerializedName("official_residentials") val official_residentials : List<Official_residentials>,
	@SerializedName("foreign_travels") val foreign_travels : List<Foreign_travels>,
	@SerializedName("additional_qualifications") val additional_qualifications : List<Additional_qualifications>,
	@SerializedName("publications") val publications : List<Publications>,
	@SerializedName("honours_awards") val honours_awards : List<Honours_awards>,
	@SerializedName("spouses") val spouses : List<Spouses>,
	//check end
	@SerializedName("disciplinary_actions") val disciplinary_actions : List<Disciplinary_actions>
)