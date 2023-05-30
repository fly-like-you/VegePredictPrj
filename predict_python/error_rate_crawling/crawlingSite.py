import re
import json
import requests
from bs4 import BeautifulSoup
from datetime import timedelta, datetime

class RegexpParser:
    def __init__(self, script):
        self.script = script

    def extract_data(self, data_key, start_index, end_index=None):
        match = re.search(r"\['{}'[\s*,.*\d(null)]*\]".format(data_key), self.script).group(0)
        search_val = re.findall(r'(\d+\.*\d*)|null', match)[start_index:end_index]
        return [int(x) if x != '' else 0 for x in search_val]

    def predict_parse(self):
        # data1: 일간 예측 가격
        return self.extract_data('data1', 9)

    def price_parse(self):
        # data3: 실제 가격
        return self.extract_data('data3', 1, 8)

class PredictionDictionary:
    def __init__(self, soup):
        self.soup = soup
        self.dict = {
            '풋고추(전체)': 1, '청양': 2, '깻잎(일반)': 4,'양파(일반)': 7, '파프리카(일반)': 12, '시금치(일반)': 15
        }

    def _parse_script(self, parse_method):
        dic = {}
        for name, parse_num in self.dict.items():
            script = self.soup.select_one(f"#grpv-{parse_num} > div > div > div > div > script").string
            parser = RegexpParser(script)
            dic[name] = parse_method(parser)
        return dic

    def predict_dict(self):
        return self._parse_script(lambda parser: parser.predict_parse())

    def price_dict(self):
        return self._parse_script(lambda parser: parser.price_parse())

class PricePredictor:
    def __init__(self, past_date):
        self.today = past_date
        self.future_date = self.today + timedelta(days=8)
        self.price_dict = self._get_price_dict()
        self.predict_dict = self._get_predict_dict()

    def _get_soup_for_date(self, date):
        url = 'https://www.gyeongnam.go.kr/bigdatafarm/priceList.es?search1=&mid=a10101000000&search2='
        search_url = url + date.strftime('%Y-%m-%d')
        response = requests.get(search_url)
        return BeautifulSoup(response.text, 'html.parser')

    def _get_price_dict(self):
        price_soup = self._get_soup_for_date(self.future_date)
        return PredictionDictionary(price_soup).price_dict()

    def _get_predict_dict(self):
        predict_soup = self._get_soup_for_date(self.today)
        return PredictionDictionary(predict_soup).predict_dict()

    def calculate_error(self):
        result = []
        for name, prices in self.price_dict.items():
            error_dict = {
                "name": name,
                "date": self.today.strftime('%Y-%m-%d'),
                "day1error": 100.0 if prices[0] == 0 else (self.predict_dict[name][0] - prices[0]) / prices[0],
                "day2error": 100.0 if prices[1] == 0 else (self.predict_dict[name][1] - prices[1]) / prices[1],
                "day3error": 100.0 if prices[2] == 0 else (self.predict_dict[name][2] - prices[2]) / prices[2],
                "day4error": 100.0 if prices[3] == 0 else (self.predict_dict[name][3] - prices[3]) / prices[3],
                "day5error": 100.0 if prices[4] == 0 else (self.predict_dict[name][4] - prices[4]) / prices[4],
                "day6error": 100.0 if prices[5] == 0 else (self.predict_dict[name][5] - prices[5]) / prices[5],
                "day7error": 100.0 if prices[6] == 0 else (self.predict_dict[name][6] - prices[6]) / prices[6],
            }
            result.append(error_dict)
        return result

    def run(self):
        return self.calculate_error()


if __name__ == "__main__":
    # 각 날자별로 오차율을 구한다
    date = datetime.today() - timedelta(days=8)
    predictor = PricePredictor(date).run()

    # json으로 만들기
    date = date.strftime('%Y-%m-%d')
    output_path = "C:\\Users\\Parkjunho\\IdeaProjects\\VegePredictPrj\\predict_python\\data\\other_site_error_rates\\"
    with open(f"{output_path}\\othSite_prediction_from_{date}.json", 'w') as f:
        json.dump(predictor, f)
