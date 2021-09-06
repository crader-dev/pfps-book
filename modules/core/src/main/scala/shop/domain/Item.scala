package shop.domain

import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID

trait Items[F[_]] {
  def findAll: F[List[Item]]
  def findBy(brandName: BrandName): F[List[Item]]
  def findById(brandId: BrandId): F[Option[Item]]
  def create(item: CreateItem): F[ItemId]
  def update(item: UpdateItem): F[Unit]
}

@newtype case class ItemId(value: UUID)
@newtype case class ItemName(value: String)
@newtype case class ItemDescription(value: String)

case class Item(
  uuid: ItemId,
  name: ItemName,
  description: ItemDescription,
  price: Money,
  brand: Brand,
  category: Category
)

case class CreateItem(
  name: ItemName,
  description: ItemDescription,
  price: Money,
  brand: Brand,
  category: Category
)

case class UpdateItem(
  uuid: ItemId,
  price: Money
)
