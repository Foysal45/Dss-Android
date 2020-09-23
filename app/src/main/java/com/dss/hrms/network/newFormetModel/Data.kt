import com.google.gson.annotations.SerializedName



data class Data (
	@SerializedName("name") val name : String,
	@SerializedName("name_bn") val name_bn : String,
	@SerializedName("photo") val photo : String,
	@SerializedName("date_of_birth") val date_of_birth : String,
	@SerializedName("fathers_name") val fathers_name : String,
	@SerializedName("fathers_name_bn") val fathers_name_bn : String,
	@SerializedName("mothers_name") val mothers_name : String,
	@SerializedName("mothers_name_bn") val mothers_name_bn : String,
	@SerializedName("promotions") val promotions : List<Promotions>,
	@SerializedName("references") val references : List<References>,
	@SerializedName("gender") val gender : Gender,
	@SerializedName("spouses") val spouses : List<Spouses>,
	@SerializedName("childs") val childs : List<Childs>,
	@SerializedName("languages") val languages : List<Languages>,
	@SerializedName("local_trainings") val local_trainings : List<Local_trainings>,
	@SerializedName("foreigntrainings") val foreigntrainings : List<Foreigntrainings>,
	@SerializedName("foreign_travels") val foreign_travels : List<Foreign_travels>,
	@SerializedName("additional_qualifications") val additional_qualifications : List<Additional_qualifications>,
	@SerializedName("publications") val publications : List<Publications>,
	@SerializedName("honours_awards") val honours_awards : List<Honours_awards>,
	@SerializedName("posting_records") val posting_records : List<Posting_records>,
	@SerializedName("official_residentials") val official_residentials : List<Official_residentials>
)