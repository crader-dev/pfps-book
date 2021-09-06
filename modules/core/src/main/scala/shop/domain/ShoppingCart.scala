package shop.domain

import io.estatico.newtype.macros.newtype
import squants.market.Money


trait ShoppingCart[F[_]] {
  def add(
    userId: UserId,
    itemId: ItemId,
    quantity: ItemQuantity
  ): F[Unit]

  def get(userId: UserId): F[CartTotal]
  def delete(userId: UserId): F[Unit]
  def removeItem(userId: UserId, itemId: ItemId): F[Unit]
  def update(userId: UserId, cart: Cart): F[Unit]
}

@newtype case class ItemQuantity(value: Int)
@newtype case class Cart(items: Map[ItemId, ItemQuantity])

case class CartItem(item: Item, quantity: ItemQuantity)
case class CartTotal(items: List[CartItem], total: Money)
