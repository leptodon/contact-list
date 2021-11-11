package ru.cactus.contactlist.domain.entities

import com.google.gson.annotations.SerializedName
import ru.cactus.contactlist.R

enum class Departments(val resTitle: Int) {

    @SerializedName("All")
    ALL(R.string.department_name_all),
    @SerializedName("android")
    ANDROID(R.string.department_name_android),
    @SerializedName("ios")
    IOS(R.string.department_name_ios),
    @SerializedName("design")
    DESIGN(R.string.department_name_design),
    @SerializedName("management")
    MANAGEMENT(R.string.department_name_management),
    @SerializedName("qa")
    QA(R.string.department_name_qa),
    @SerializedName("back_office")
    BACK_OFFICE(R.string.department_name_back_office),
    @SerializedName("frontend")
    FRONTEND(R.string.department_name_frontend),
    @SerializedName("hr")
    HR(R.string.department_name_hr),
    @SerializedName("pr")
    PR(R.string.department_name_pr),
    @SerializedName("backend")
    BACKEND(R.string.department_name_backend),
    @SerializedName("support")
    SUPPORT(R.string.department_name_support),
    @SerializedName("analytics")
    ANALYTICS(R.string.department_name_analytics)
}