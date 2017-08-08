# -*- coding: UTF-8 -*-
import time
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait # available since 2.4.0
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC # available since 2.26.0
from selenium.webdriver.support.select import Select
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
import sys
import logging
import json
from pprint import pprint

#函数定义  begin 
#等待id为elm_id的元素可见
def _wait_for_elm_by_id(elm_id,timeout = 3600):
    _check_alert_to_close()
    logging.info("Waiting element shown, id: " + elm_id)
    element = WebDriverWait(driver, timeout).until(
        EC.visibility_of_element_located((By.ID, elm_id))
    )
	
def _wait_for_elm_by_name(name,timeout = 3600):
    _check_alert_to_close()
    logging.info("Waiting element shown, name: " + name)
    element = WebDriverWait(driver, timeout).until(
        EC.visibility_of_element_located((By.NAME, name))
    )

#点击确认关闭alert弹出框
def _check_alert_to_close():
    try:
        alert = driver.switch_to_alert()
        alert.accept()
        logging.info("Alert accepted")
    except:
        pass

#填写name为elmId的输入框，如果输入框已经有值则忽略
def _textInputByName(elmId,val,timeout=3600,duplicate_substr=None):
	
    if val == None:
	    val=''
    else:
	    logging.info("Waiting Input name: " + elmId)
    _check_alert_to_close()
    #等待该id的元素就绪(页面存在)
    
    elm_ipt_x = WebDriverWait(driver, timeout).until(
        EC.presence_of_element_located((By.NAME, elmId))
    )
    
    #获取input框原来的value值
    elm_ipt_val = elm_ipt_x.get_attribute("value");
    if elm_ipt_val == "":
        elm_ipt_x.send_keys(val)
    else:
        logging.info(elmId + " value is not empty,ignore sendkeys")
    logging.info("\"" + elmId + "\"" + ":" + "\"" + val + "\",")

#填写id为elmId的输入框，如果输入框已经有值则忽略
def _textInputById(elmId,val,timeout=3600,duplicate_substr=None):
	
    if val == None:
	    val=''
    else:
	    logging.info("Waiting Input name: " + elmId)
    _check_alert_to_close()
    #等待该id的元素就绪(页面存在)
    
    elm_ipt_x = WebDriverWait(driver, timeout).until(
        EC.presence_of_element_located((By.ID, elmId))
    )
    
    #获取input框原来的value值
    elm_ipt_val = elm_ipt_x.get_attribute("value");
    if elm_ipt_val == "":
        elm_ipt_x.send_keys(val)
    else:
        logging.info(elmId + " value is not empty,ignore sendkeys")
    logging.info("\"" + elmId + "\"" + ":" + "\"" + val + "\",")

#点击id为elmId的按钮
def _buttonById(elmId,timeout=3600):
    _check_alert_to_close()
    logging.info("Waiting Button id: " + elmId)
    elm_btn_x = WebDriverWait(driver, timeout).until(
        EC.presence_of_element_located((By.ID, elmId))
    )
    elm_btn_x.click()
    logging.info("Click Button id: " + elmId)
	
#個人名簿登録
def _buttonByCustomerName(customerName,buttonName="個人名簿登録"):
    target=driver.find_element_by_xpath("//tr/td/div[contains(text(),'"+customerName+"')]/../../following-sibling::tr[1]/td/input[@value='"+buttonName+"']")
    driver.execute_script("arguments[0].scrollIntoView();", target) #拖动到可见的元素去
    target.click()
	
#点击name为elmName的按钮
def _buttonByName(elmName,timeout=3600):
    _check_alert_to_close()
    logging.info("Waiting Button name: " + elmName)
    elm_btn_x = driver.find_element_by_name(elmName)
	driver.execute_script("arguments[0].scrollIntoView();", target) #拖动到元素去
    elm_btn_x.click()
    logging.info("Click Button name: " + elmName)

#勾选单选组
def _radioBoxVisableByName(radioName,value,isSelectId1="false",timeout=3600):
    _check_alert_to_close()
    elm_ipt_x = driver.find_element_by_xpath("//input[@name='"+radioName+"' and @value='"+value+"']")
    #是否选中第一个
    if isSelectId1:
        elm_ipt_x = driver.find_element_by_xpath("//body/descendant::input[@name='"+radioName+"'][1]") ;    
    elm_ipt_x.click()
	
#函数定义  end
#主流程开始
# Main Process Start
if not sys.platform.startswith('win'):
    import coloredlogs
    coloredlogs.install(level='INFO')
logging.basicConfig(format='%(levelname)s:%(message)s', level=logging.INFO)

#import JSON data file,从文件加载json数据，命令行的第二个参数为完整文件名
data_json = None
file_name = sys.argv[1]
logging.info("Import json data from file : %s",file_name)
with open(file_name) as data_file:
    data_json = json.load(data_file)

# 打开火狐浏览器并且跳转到签证网站
profileDir = "C:/Users/user/AppData/Roaming/Mozilla/Firefox/Profiles/e0dheleu.default"
profile = webdriver.FirefoxProfile(profileDir)
driver = webdriver.Firefox(profile)

driver.get("https://churenkyosystem.com/member/login.php")

#1. login
answer = data_json["visaAccount"]
if not answer:
    answer = "1507-001"
_textInputByName("LOGIN_ID",answer)

answer = data_json["visaPasswd"]
if not answer:
    answer = "kintsu2017"
	
_textInputByName("PASSWORD",answer)
_buttonByName("SUBMIT_LOGIN_x")

#2. text input applicant info 
# Make sure page has been complete loaded
_wait_for_elm_by_id("container")
driver.find_element_by_xpath("//li[@class='navi_05']").click()

time.sleep(3)
_wait_for_elm_by_id("container")
logging.info("Load Page Title :%s",driver.title)

#指定番号
answer = data_json["agentNo"]
if not answer:
    answer = "GTP-BJ-084-0"

_textInputById("CHINA_AGENT_CODE",answer)
_buttonByName("BTN_SEARCH")

#签证类型
answer = data_json["visaType1"]
if not answer:
    answer = "2"
elm_ipt_x = driver.find_element_by_xpath("//input[@name='VISA_TYPE_1' and @value='"+answer+"']/..")
elm_ipt_x.click()

#申请人姓名
answer = data_json["proposerNameCN"]
_textInputByName("APPLICANT_NAME",answer)

answer = data_json["proposerNameEN"]
_textInputByName("APPLICANT_PINYIN",answer)

#人数
answer = data_json["applicantCnt"]
_textInputByName("NUMBER_OF_TOURISTS",answer)

#出入境时间
answer = data_json["entryDate"]
pprint("entryDate:" + answer)
driver.execute_script("document.getElementById('ARRIVAL_DATE').removeAttribute('readonly',0);")

arrivalDate = driver.find_element_by_id("ARRIVAL_DATE")
arrivalDate.clear()
arrivalDate.send_keys(answer)
arrivalDate.send_keys(Keys.TAB)

answer = data_json["leaveDate"]
pprint("leaveDate:" + answer)
driver.execute_script("document.getElementById('DEPARTURE_DATE').removeAttribute('readonly',0);")

departureDate = driver.find_element_by_id("DEPARTURE_DATE")
departureDate.clear()
departureDate.send_keys(answer)
departureDate.send_keys(Keys.TAB)
_check_alert_to_close()

#点击确认
_check_alert_to_close()
_buttonByName("BTN_CHECK_x")

#确认页
time.sleep(3)
_wait_for_elm_by_id("container")
_radioBoxVisableByName("MAIL_STATUS","1")
_buttonByName("BTN_CHECK_SUBMIT_x")
_check_alert_to_close()

#3. 列表页，点击個人名簿登録
# Make sure page has been complete loaded
_wait_for_elm_by_id("container")
driver.find_element_by_xpath("//li[@class='navi_01']").click()
time.sleep(3)
_wait_for_elm_by_id("container")
logging.info("Load Page Title :%s",driver.title)

#名簿登録
answer = data_json["proposerNameCN"]
_wait_for_elm_by_id("container",3600)
_buttonByCustomerName(answer)
_buttonByName("BTN_ADD_CSV_x")

# Upload excel page
# Make sure page has been complete loaded
time.sleep(3)
_wait_for_elm_by_id("container")
logging.info("Load Page Title :%s",driver.title)

#上传名簿
answer = data_json["excelUrl"]
_textInputByName("CSV_FILE",answer)
_buttonByName("BTN_SUBMIT_x")
_check_alert_to_close()

#归国报告
_wait_for_elm_by_id("container")
driver.find_element_by_xpath("//li[@class='navi_01']").click()
time.sleep(3)
_wait_for_elm_by_id("container")

answer = data_json["proposerNameCN"]
_wait_for_elm_by_id("container",3600)
_buttonByCustomerName(answer,"帰国報告")
_wait_for_elm_by_id("container")
_buttonByName("BTN_CHECK_x")

_wait_for_elm_by_id("container")
_buttonByName("BTN_CHECK_SUBMIT_x")

