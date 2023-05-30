import time
from selenium import webdriver
from utils.VegetableDataDownloader import VegetableDataDownloader
from utils.PriceJsonParser import PriceJsonParser


project_path = 'C:\\Users\\Parkjunho\\IdeaProjects\\VegePredictPrj\\predict_python\\'
destination_path = project_path + 'price_predict\\vegetable_price\\original_vegetable_csv'
output_path = project_path + "data\\price_and_trade\\"

def get_options():
    options = webdriver.ChromeOptions()

    # 브라우저의 다운로드 설정을 변경합니다.
    preferences = {
        "download.default_directory": destination_path,  # 다운로드 경로를 설정합니다.
        "profile.default_content_setting_values.automatic_downloads": 1,
    }
    options.add_argument("--headless")
    options.add_experimental_option("prefs", preferences)
    return options

if __name__ == '__main__':
    # Selenium 웹 드라이버 경로
    webdriver_path = 'C:/Users/Parkjunho/IdeaProjects/VegePredictPrj/predict_python/nongnet_crawling/utils/chromedriver_win32.exe'
    nongnet_url = 'https://www.nongnet.or.kr/front/M000000049/content/view.do'

    # Chrome 드라이버 시작
    driver = webdriver.Chrome(options=get_options())
    driver.get(nongnet_url)

    time.sleep(3)
    vegetable_list = ['미나리', '풋고추', '청양고추', '깻잎', '시금치', '파프리카', '양파', '토마토']


    for vege in vegetable_list:
        try:
            downloader = VegetableDataDownloader(driver, vege, sleep_time=3)
            downloader.do_download()
        except:
            print("fail! try again.")
            try:
                downloader = VegetableDataDownloader(driver, vege, sleep_time=4)
                downloader.do_download()
            except:
                print("fail! try again.")
                downloader = VegetableDataDownloader(driver, vege, sleep_time=5)
                downloader.do_download()


    driver.quit()  # 셀레니움 크롤링 종료

    # 크롤링한 파일을 json으로 저장하기
    json_parser = PriceJsonParser(destination_path + "\\")
    json_parser.parse_csv_to_json(output_path)




