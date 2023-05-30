import sys
import os
sys.path.append(os.getcwd())
from .VegetablePriceReader import VegetablePriceReader, create_today_df

from datetime import datetime
import glob
import pandas as pd
import json


class PriceJsonParser:
    def __init__(self, csv_path):
        self.csv_path = csv_path
        self.df = None

    def parse_csv_to_json(self, output_path):
        self.make_data_frame()
        data_list = self.transform_to_list()
        self.save(data_list, output_path)

    def make_data_frame(self):  # csv파일에서 농산물별로 가격, 거래량 데이터를 가져와서 데이터프레임으로 만든다.
        csv_files = glob.glob(self.csv_path + '*.csv')

        df = create_today_df()
        for csv_path in csv_files:
            reader = VegetablePriceReader(csv_path)
            new_df = reader.process()
            df = pd.concat([df, new_df], axis=1)

        df['일자'] = df['일자'].astype(str)
        self.df = df

    def transform_to_list(self):  # 자바에서 사용하기 쉬운 json 형식을 제작한다.
        # 데이터 리스트 초기화
        data_list = []
        vegetable_names = ['토마토(일반)', '양파(일반)', '파프리카(일반)', '시금치(일반)', '깻잎(일반)', '청양', '풋고추(전체)', '미나리(일반)']
        # 각 품목에 대해 데이터 생성
        for index, row in self.df.iterrows():
            data = {'date': row['일자']}
            for name in vegetable_names:
                trade = f'{name} 거래물량(톤)'
                price = f'{name} 평균가격(원/kg)'
                data[name] = {
                    'trade': row[trade],
                    'price': row[price],
                }
            data_list.append(data)
        return data_list

    def save(self, data_list, output_path):  # 경로에 맞게 json을 파일로 저장한다.
        today = datetime.today().strftime('%Y-%m-%d')
        with open(output_path + f"price_trade_{today}.json", 'w') as file:
            json.dump(data_list, file)