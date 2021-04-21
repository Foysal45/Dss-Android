package com.dss.hrms.model

data class SpecificDistrictModel(
    val id: Int?,
    val is_cht: Int?,
    val division_id: Int?,
    val name: String?,
    val name_bn: String?,
    val upazilas: List<Upazilas>?,
    val city_corporations: List<CityCorporations>?,
    val municipalities: List<Municipalities>?
)

data class Upazilas(
    val id: Int?,
    val is_island: Int?,
    val district_id: Int?,
    val name: String?,
    val name_bn: String?,
    val municipalities: List<Municipalities>?
)
data class Union(
    val id: Int?,
    val is_municipality: Int?,
    val upazila_id: Int?,
    val name: String?,
    val name_bn: String?
)

data class Municipalities(
    val id: Int?,
    val upazila_id: Int?,
    val is_municipality: Int?,
    val name: String?,
    val name_bn: String?
)

data class CityCorporations(
    val id: Int?,
    val division_id: Int?,
    val district_id: Int?,
    val name: String?,
    val name_bn: String?
)