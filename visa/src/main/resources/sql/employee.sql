/*employee_list_data*/
SELECT
	ve.id,
	ve.comId,
	ve.fullName,
	ve.telephone,
	ve.`password`,
	ve.userType,
	ve.qq,
	ve.landline,
	ve.email,
	ve.department,
	ve.job,
	ve.disableUserStatus,
	ve.`status`,
	ve.createTime,
	ve.updateTime,
	ve.remark,
	ve.res1,
	ve.res2,
	ve.res3,
	ve.res4,
	ve.res5
FROM
	visa_employee ve
$condition